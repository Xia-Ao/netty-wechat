package com.xiaao.wechat.pojo.bo;
/**
 * @author: Xia-ao
 * @create: 2018-12-02 21:31
 **/

/**
 * @className: UsersBo
 * @description: 接收前端提交信息
 * @author: Xia-ao
 * @create: 2018-12-02 21:31
 **/
public class UsersBo {

    private String userId;
    private String faceData;
    private String nickname;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFaceData() {
        return faceData;
    }

    public void setFaceData(String faceData) {
        this.faceData = faceData;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
