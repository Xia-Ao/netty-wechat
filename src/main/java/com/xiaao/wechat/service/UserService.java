package com.xiaao.wechat.service;
/**
 * @author: Xia-ao
 * @create: 2018-12-02 21:04
 **/


import com.xiaao.wechat.netty.ChatMessage;
import com.xiaao.wechat.pojo.ChatMsg;
import com.xiaao.wechat.pojo.Users;
import com.xiaao.wechat.pojo.vo.FriendRequestVo;
import com.xiaao.wechat.pojo.vo.MyFriendsVo;

import java.util.List;

/**
 * @Interface: UserService
 * @description: 用户信息服务
 * @author: Xia-ao
 * @create: 2018-12-02 21:04
 **/

public interface UserService {

    /**
     * @Name:
     * @Description: 判断用户名是否存在
     * @Param:
     * @return:
     **/
    public boolean queryUserNameIsExist(String userName);

    /**
     * @Description: 登录验证
     **/
    public Users queryUserForLogin(String userName, String pwd);

    /**
     * @Description: 用户注册
     **/
    public Users saveUser(Users user);

    /**
     * @Description: 更新用户头像
     **/
    public Users updateUserInfo(Users user);

    /**
     * @Description: 搜索朋友的前置条件
     **/
    public Integer preconditionSearchFriends(String myUserId, String friendUserName);

    /**
     * @Description: 通过用户名查询用户信息
     **/
    public Users queryUserInfoByUsername(String username);

    /**
     * @Description: 添加用户好友请求
     **/
    public void sendFriendRequest(String myUserId, String friendUserName);


    /**
     * @Description: 查询好友请求列表
     **/
    public List<FriendRequestVo> queryFriendRequestList(String acceptUserId);


    /**
     * @Description: 删除用户请求记录
     **/
    public void deleteFriendRequest(String sendUserId, String acceptUserId);

    /**
     * @Description: 通过用户请求
     **/
    public void passFriendRequest(String sendUserId, String acceptUserId);

    /**
     * @Description: 请求用户通讯录好友
     **/
    public List<MyFriendsVo> queryMyFriends(String userId);


    /**
     * @Description: 保存聊天消息到数据库
     **/
    public String saveMsg(ChatMessage chatMessage);

    /**
     * @Description: 批量签收消息
     **/
    public void updateMsgSigned(List<String> msgIdList);

    /**
     * @Description: 获取未签收的消息
     **/
    public  List<ChatMsg> getUnReadMsgList(String acceptUserId);

}
