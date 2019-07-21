package com.xiaao.wechat.controller;
/**
 * @author: Xia-ao
 * @create: 2018-12-01 21:40
 **/

import com.xiaao.wechat.enums.OperatorFriendRequestTypeEnum;
import com.xiaao.wechat.enums.SearchFriendsStatusEnum;
import com.xiaao.wechat.netty.ChatMessage;
import com.xiaao.wechat.pojo.ChatMsg;
import com.xiaao.wechat.pojo.Users;
import com.xiaao.wechat.pojo.bo.UsersBo;
import com.xiaao.wechat.pojo.vo.FriendRequestVo;
import com.xiaao.wechat.pojo.vo.MyFriendsVo;
import com.xiaao.wechat.pojo.vo.UsersVo;
import com.xiaao.wechat.service.UserService;
import com.xiaao.wechat.utils.FastDFSClient;
import com.xiaao.wechat.utils.FileUtils;
import com.xiaao.wechat.utils.IMoocJSONResult;
import com.xiaao.wechat.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.util.List;

/**
 * @className: UserController
 * @description: 用户访问控制器
 * @author: Xia-ao
 * @create: 2018-12-01 21:40
 **/
@RestController
@RequestMapping("/u")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FastDFSClient fastDFSClient;

    @GetMapping("/test")
    public String test() {
        return "test 页面";
    }

    /**
     * @Description: 用户注册或登录
     **/
    @PostMapping("/registerOrLogin")
    public IMoocJSONResult registerOrLogin(@RequestBody Users user) throws Exception {

        // 0. 判断用户名密码不能为空
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            return IMoocJSONResult.errorMsg("用户名密码不能为空。。。");
        }

        // 1. 判断用户名是否存在，如果存在就登录，否则注册
        boolean userNameIsExist = userService.queryUserNameIsExist(user.getUsername());

        Users userResult = null;
        if (userNameIsExist) {
            // 1.1 登录
            userResult = userService.queryUserForLogin(user.getUsername(), MD5Utils.getMD5Str(user.getPassword()));
            if (userResult == null) {
                return IMoocJSONResult.errorMsg("用户名或密码不正确。..");
            }


        } else {
            //    1.2 注册
            user.setNickname(user.getUsername());
            user.setFaceImage("");
            user.setFaceImageBig("");
            user.setPassword(MD5Utils.getMD5Str(user.getPassword()));

            userResult = userService.saveUser(user);
        }

        // 去除不相干的信息
        UsersVo usersVo = new UsersVo();
        BeanUtils.copyProperties(userResult, usersVo);  // 复制到输出


        return IMoocJSONResult.ok(usersVo);

    }


    @PostMapping("/uploadFaceBase64")
    public IMoocJSONResult uploadFaceBase64(@RequestBody UsersBo userBo) throws Exception {

        // 获取前端传过来的base64字符串, 然后转换为文件对象再上传
        String base64Data = userBo.getFaceData();
        String userFacePath = "F:\\user" + userBo.getUserId() + "userface64.png";
        FileUtils.base64ToFile(userFacePath, base64Data);

        // 上传文件到fastDFS
        MultipartFile faceFile = FileUtils.fileToMultipart(userFacePath);
        String url = fastDFSClient.uploadBase64(faceFile);
        System.out.println(url);

        // 获取缩略图的URL
        String thump = "_80x80.";
        String arr[] = url.split("\\.");
        String thumpUrl = arr[0] + thump + arr[1];

        // 更新用户头像
        Users user = new Users();
        user.setId(userBo.getUserId());
        user.setFaceImage(thumpUrl);
        user.setFaceImageBig(url);

        Users result = userService.updateUserInfo(user);

        // 去除不相干的信息
        UsersVo usersVo = new UsersVo();
        BeanUtils.copyProperties(result, usersVo);  // 复制到输出


        return IMoocJSONResult.ok(usersVo);
    }

    /**
     * @Description: 修改昵称
     **/
    @PostMapping("/setNickName")
    public IMoocJSONResult setNickName(@RequestBody UsersBo usersBo) throws Exception {

        Users user = new Users();
        user.setId(usersBo.getUserId());
        user.setNickname(usersBo.getNickname());

        Users result = userService.updateUserInfo(user);

        return IMoocJSONResult.ok(result);
    }

    /**
     * @Description: 搜索好友
     **/
    @PostMapping("/search")
    public IMoocJSONResult searchUser(String myUserId, String friendUserName) throws Exception {

        /// 0 判断myUserId， friendUserName 不能为空
        if (StringUtils.isBlank(myUserId) || StringUtils.isBlank(friendUserName)) {
            return IMoocJSONResult.errorMsg("");
        }

        // 前置条件- 1 搜索的用户如果不存在，返回[无此用户]
        // 前置条件- 2 搜索的用户是自己，返回[不能添加自己]
        // 前置条件- 3 搜索的用户已经是我的好友，返回[该用户已经是我的好友]
        Integer status = userService.preconditionSearchFriends(myUserId, friendUserName);
        // 查询成功
        if (status == SearchFriendsStatusEnum.SUCCESS.status) {
            Users user = userService.queryUserInfoByUsername(friendUserName);
            UsersVo usersVo = new UsersVo();
            BeanUtils.copyProperties(user, usersVo);
            return IMoocJSONResult.ok(usersVo);
        } else {  // 查询失败
            String errMsg = SearchFriendsStatusEnum.getMsgByKey(status);
            return IMoocJSONResult.ok(errMsg);
        }
    }

    /**
     * @Description: 添加好友请求
     **/
    @PostMapping("/addFriendRequest")
    public IMoocJSONResult addFriendRequest(String myUserId, String friendUserName) throws Exception{

        // 0 判断myUserId， friendUserName 不能为空
        if (StringUtils.isBlank(myUserId) || StringUtils.isBlank(friendUserName)) {
            return IMoocJSONResult.errorMsg("");
        }
        // 前置条件- 1 搜索的用户如果不存在，返回[无此用户]
        // 前置条件- 2 搜索的用户是自己，返回[不能添加自己]
        // 前置条件- 3 搜索的用户已经是我的好友，返回[该用户已经是我的好友]
        Integer status = userService.preconditionSearchFriends(myUserId, friendUserName);

        if (status == SearchFriendsStatusEnum.SUCCESS.status) {  // 查询成功,添加好友
           userService.sendFriendRequest(myUserId, friendUserName);
        } else {  // 查询失败
            String errMsg = SearchFriendsStatusEnum.getMsgByKey(status);
            return IMoocJSONResult.ok(errMsg);
        }

        return IMoocJSONResult.ok();    // 添加成功
    }

    /**
     * @Description: 添加好友接口请求
     **/
    @PostMapping("/queryFriendsRequest")
    public IMoocJSONResult queryFriendRequest(String userId){

        // 0 判断参数不能为空
        if (StringUtils.isBlank(userId) ) {
            return IMoocJSONResult.errorMsg("");
        }
        // 1. 查询用户接受的朋友申请
        List<FriendRequestVo> result = userService.queryFriendRequestList(userId);
        return IMoocJSONResult.ok(result);

    }

    /**
     * @Description: 操作-忽略/同意朋友请求
     **/
    @PostMapping("/opFriendRequest")
    public IMoocJSONResult opFriendRequest(String acceptUserId, String sendUserId, Integer opType){
        // 0 判断 参数 不能为空
        if (StringUtils.isBlank(acceptUserId)||StringUtils.isBlank(sendUserId)|| opType==null ) {
            return IMoocJSONResult.errorMsg("");
        }

        // 1. 如果opType没有对应的枚举值，则直接抛出空错误信息
        if(StringUtils.isBlank(OperatorFriendRequestTypeEnum.getMsgByType(opType))){
            return IMoocJSONResult.errorMsg("");
        }

        // 2. 如果判断为 忽略 好友请求，则直接删除好友请求的数据库表记录
        if(opType== OperatorFriendRequestTypeEnum.IGNORE.type){
            userService.deleteFriendRequest(sendUserId,acceptUserId);

        }else if(opType == OperatorFriendRequestTypeEnum.PASS.type){
            // 3. 如果判断为 通过 好友请求，互相增加好友记录到数据库对应的表， 然后删除好友请求的数据库表记录
            userService.passFriendRequest(sendUserId,acceptUserId);
        }

       /* // 4. 数据库表查询好友列表
        List<MyFriendsVo> myFriends= userService.queryFriends(acceptUserId);*/

        return IMoocJSONResult.ok();
    }

    /**
     * @Description: 通讯录好友列表
     **/
    @PostMapping("/myFriends")
    public IMoocJSONResult mgFriends (String userId){
        // 0. userId 判断不能为空
        if(StringUtils.isBlank(userId)){
            return IMoocJSONResult.errorMsg("");
        }
        // 1. 数据库查询好友
        List<MyFriendsVo> myfriend= userService.queryMyFriends(userId);

        return IMoocJSONResult.ok(myfriend);
    }

    /**
     * @Description: 用户手机端获取未签收的消息列表
     **/
    @PostMapping("/getUnReadMsgList")
    public IMoocJSONResult getUnReadMsgList(String acceptUserId){
        // 0. userId 判断不能为空
        if(StringUtils.isBlank(acceptUserId)){
            return IMoocJSONResult.ok("");
        }

        // 查询列表
        List<ChatMsg> unReadList = userService.getUnReadMsgList(acceptUserId);
        return IMoocJSONResult.ok(unReadList);
    }
}
