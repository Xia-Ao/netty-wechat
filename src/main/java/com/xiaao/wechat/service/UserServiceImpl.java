package com.xiaao.wechat.service;
/**
 * @author: Xia-ao
 * @create: 2018-12-02 21:06
 **/

import com.xiaao.wechat.enums.MsgSignFlagEnum;
import com.xiaao.wechat.enums.SearchFriendsStatusEnum;
import com.xiaao.wechat.mapper.*;
import com.xiaao.wechat.netty.ChatMessage;
import com.xiaao.wechat.pojo.ChatMsg;
import com.xiaao.wechat.pojo.FriendsRequest;
import com.xiaao.wechat.pojo.MyFriends;
import com.xiaao.wechat.pojo.Users;
import com.xiaao.wechat.pojo.vo.FriendRequestVo;
import com.xiaao.wechat.pojo.vo.MyFriendsVo;
import com.xiaao.wechat.utils.FastDFSClient;
import com.xiaao.wechat.utils.FileUtils;
import com.xiaao.wechat.utils.QRCodeUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @className: UserServiceImpl
 * @description: 用户信息服务接口实现
 * @author: Xia-ao
 * @create: 2018-12-02 21:06
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private MyFriendsMapper myFriendsMapper;

    @Autowired
    private FriendsRequestMapper friendsRequestMapper;

    @Autowired
    private UsersMapperCustom usersMapperCustom;

    @Autowired
    private ChatMsgMapper chatMsgMapper;

    @Autowired
    private Sid sid;

    @Autowired
    private QRCodeUtils qrCodeUtils;

    @Autowired
    private FastDFSClient fastDFSClient;

    /**
     * @Description: 查询用户是否存在
     **/
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUserNameIsExist(String userName) {

        Users user = new Users();
        user.setUsername(userName);

        Users result = usersMapper.selectOne(user);


        return result != null ? true : false;
    }

    /**
     * @Description: 登录查询验证
     **/
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String userName, String pwd) {
        // 使用Example 对象
        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        // 判断是否匹配
        criteria.andEqualTo("username", userName);
        criteria.andEqualTo("password", pwd);

        Users userResult = usersMapper.selectOneByExample(userExample);

        return userResult;
    }

    /**
     * @Description: 用户注册
     **/
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users saveUser(Users user) {

        String userId = sid.nextShort();

        // 为每个用户生成唯一的二维码
        String qrCodePath = "F:\\user" + userId + "qrcode.png";
        qrCodeUtils.createQRCode(qrCodePath, "weixin_qrcode:" + user.getUsername());
        MultipartFile qrCodeFile = FileUtils.fileToMultipart(qrCodePath);

        String qrCodeUrl = "";
        try {
            qrCodeUrl = fastDFSClient.uploadQRCode(qrCodeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        user.setQrcode(qrCodeUrl);
        user.setId(userId);
        usersMapper.insert(user);

        return user;
    }

    /**
     * @Description: 更新用户信息
     **/
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users updateUserInfo(Users user) {

        usersMapper.updateByPrimaryKeySelective(user);
        return queryUserById(user.getId());
    }

    //    @Transactional(propagation = Propagation.SUPPORTS)
    private Users queryUserById(String userId) {
        return usersMapper.selectByPrimaryKey(userId);
    }


    /**
     * @Description: 搜索朋友的前置条件
     * return： 返回状态码
     **/
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer preconditionSearchFriends(String myUserId, String friendUserName) {

        Users user = queryUserInfoByUsername(friendUserName);

        // 1. 搜索用户如果不存在，返回[无此用户]
        if (user == null) {
            return SearchFriendsStatusEnum.USER_NOT_EXIST.status;
        }

        // 2. 搜索用户如果是自己，返回[不能能添加自己]
        if (user.getId().equals(myUserId)) {
            return SearchFriendsStatusEnum.NOT_YOURSELF.status;
        }

        // 3. 搜索用户如果已经是用户好友，返回[已经是好友]
        Example mfe = new Example(MyFriends.class);
        Example.Criteria mfc = mfe.createCriteria();
        mfc.andEqualTo("myUserId", myUserId);
        mfc.andEqualTo("myFriendsUserId", user.getId());

        MyFriends myFriendsRel = myFriendsMapper.selectOneByExample(mfe);
        if (myFriendsRel != null) {
            return SearchFriendsStatusEnum.ALREADY_FRIENDS.status;
        }

        return SearchFriendsStatusEnum.SUCCESS.status;
    }


    /**
     * @Description: 通过用户名查询用户信息
     **/
    public Users queryUserInfoByUsername(String username) {

        Example ue = new Example(Users.class);
        Example.Criteria uc = ue.createCriteria();
        uc.andEqualTo("username", username);
        return usersMapper.selectOneByExample(ue);
    }

    /**
     * @Description: 添加用户好友请求
     **/
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void sendFriendRequest(String myUserId, String friendUserName) {

        // 0.根据用户名查出请求好友信息
        Users friend = queryUserInfoByUsername(friendUserName);

        // 1.查询发送好友请求记录表
        Example fre = new Example(FriendsRequest.class);
        Example.Criteria frc = fre.createCriteria();
        frc.andEqualTo("sendUserId", myUserId);
        frc.andEqualTo("acceptUserId", friend.getId());
        FriendsRequest friendsRequestResult = friendsRequestMapper.selectOneByExample(fre);
        if (friendsRequestResult == null) {
            // 1.1 如果不是我的好友，并且还有记录没有添加，则新增好友请求记录
            String requestId = sid.nextShort();     // 生成唯一的请求ID

            FriendsRequest friendRequest = new FriendsRequest();
            friendRequest.setId(requestId);
            friendRequest.setSendUserId(myUserId);
            friendRequest.setAcceptUserId(friend.getId());
            friendRequest.setRequestDateTime(new Date());
            friendsRequestMapper.insert(friendRequest);
        }

    }


    /**
     * @Description: 查询好友请求列表
     **/
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<FriendRequestVo> queryFriendRequestList(String acceptUserId) {
        return usersMapperCustom.queryFriendRequestList(acceptUserId);
    }

    /**
     * @Description: 删除添加好友请求记录
     **/
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteFriendRequest(String sendUserId, String acceptUserId) {
        Example fre = new Example(FriendsRequest.class);
        Example.Criteria frc = fre.createCriteria();
        frc.andEqualTo("sendUserId", sendUserId);
        frc.andEqualTo("acceptUserId", acceptUserId);
        friendsRequestMapper.deleteByExample(fre);
    }

    /**
     * @Description: 通过添加好友请求记录
     **/
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void passFriendRequest(String sendUserId, String acceptUserId) {

        // 相互保存好友记录
        saveFriends(sendUserId, acceptUserId);
        saveFriends(acceptUserId, sendUserId);
        deleteFriendRequest(sendUserId, acceptUserId);       // 删除添加好友请求记录


    }

    /**
     * @Description: 保存好友
     **/
    private void saveFriends(String sendUserId, String acceptUserId) {

        MyFriends myFriends = new MyFriends();
        String Id = sid.nextShort();
        myFriends.setId(Id);
        myFriends.setMyFriendsUserId(acceptUserId);
        myFriends.setMyUserId(sendUserId);
        myFriendsMapper.insert(myFriends);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<MyFriendsVo> queryMyFriends(String userId) {
        return usersMapperCustom.queryMyFriends(userId);
    }


    /**
     * @Description: 保存聊天消息到数据库
     **/
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String saveMsg(ChatMessage chatMessage) {

        ChatMsg msgDB = new ChatMsg();
        System.out.println(msgDB);
        String msgId = sid.nextShort();
        msgDB.setId(msgId);
        msgDB.setAcceptUserId(chatMessage.getReceiverId());
        msgDB.setSendUserId(chatMessage.getSenderId());
        msgDB.setCreateTime(new Date());
        msgDB.setSignFlag(MsgSignFlagEnum.unsign.type);
        msgDB.setMsg(chatMessage.getMsg());

        chatMsgMapper.insert(msgDB);

        return msgId;
    }

    /**
     * @Description: 批量签收消息
     **/
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateMsgSigned(List<String> msgIdList) {
        usersMapperCustom.batchUpdateMsgSigned(msgIdList);
    }

    /**
     * @Description: 获取未签收的消息
     **/
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ChatMsg> getUnReadMsgList(String acceptUserId) {
        Example chatExample = new Example(ChatMsg.class);
        Example.Criteria chatCriteria = chatExample.createCriteria();
        chatCriteria.andEqualTo("signFlag", 0);
        chatCriteria.andEqualTo("acceptUserId", acceptUserId);

        List<ChatMsg> result  = chatMsgMapper.selectByExample(chatExample);

        return result;
    }
}
