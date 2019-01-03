package com.xiaao.wechat.pojo.vo;
/**
 * @author: Xia-ao
 * @create: 2018-12-02 21:31
 **/

/**
 * @className: FriendRequestVo
 * @description: 好友请求
 * @author: Xia-ao
 * @create: 2018-12-02 21:31
 **/
public class MyFriendsVo {

    private String friendUserId;
    private String friendUsername;
    private String friendFaceImage;
    private String friendNickname;

    public String getFriendUserId() {
        return friendUserId;
    }

    public void setFriendUserId(String friendUserId) {
        this.friendUserId = friendUserId;
    }

    public String getFriendUsername() {
        return friendUsername;
    }

    public void setFriendUsername(String friendUsername) {
        this.friendUsername = friendUsername;
    }

    public String getFriendFaceImage() {
        return friendFaceImage;
    }

    public void setFriendFaceImage(String friendFaceImage) {
        this.friendFaceImage = friendFaceImage;
    }

    public String getFriendNickname() {
        return friendNickname;
    }

    public void setFriendNickname(String friendNickname) {
        this.friendNickname = friendNickname;
    }
}
