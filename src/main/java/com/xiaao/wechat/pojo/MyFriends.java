package com.xiaao.wechat.pojo;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "my_friends")
public class MyFriends {
    /**
     * 我的朋友列表id
     */
    private String id;

    /**
     * 我的用户id
     */
    @Column(name = "my_user_id")
    private String myUserId;

    /**
     * 我的朋友用户id
     */
    @Column(name = "my_friends_user_id")
    private String myFriendsUserId;

    /**
     * 获取我的朋友列表id
     *
     * @return id - 我的朋友列表id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置我的朋友列表id
     *
     * @param id 我的朋友列表id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取我的用户id
     *
     * @return my_user_id - 我的用户id
     */
    public String getMyUserId() {
        return myUserId;
    }

    /**
     * 设置我的用户id
     *
     * @param myUserId 我的用户id
     */
    public void setMyUserId(String myUserId) {
        this.myUserId = myUserId;
    }

    /**
     * 获取我的朋友用户id
     *
     * @return my_friends_user_id - 我的朋友用户id
     */
    public String getMyFriendsUserId() {
        return myFriendsUserId;
    }

    /**
     * 设置我的朋友用户id
     *
     * @param myFriendsUserId 我的朋友用户id
     */
    public void setMyFriendsUserId(String myFriendsUserId) {
        this.myFriendsUserId = myFriendsUserId;
    }
}