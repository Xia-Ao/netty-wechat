package com.xiaao.wechat.pojo;

import javax.persistence.Column;
import javax.persistence.Id;

public class Users {
    /**
     * 用户id
     */
    @Id
    private String id;

    /**
     * 用户名
     */
    private String username;

    private String password;

    /**
     * 头像缩略图
     */
    @Column(name = "face_image")
    private String faceImage;

    /**
     * 头像完整图
     */
    @Column(name = "face_image_big")
    private String faceImageBig;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 二维码
     */
    private String qrcode;

    /**
     * 设备id
     */
    private String cid;

    /**
     * 获取用户id
     *
     * @return id - 用户id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置用户id
     *
     * @param id 用户id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取头像缩略图
     *
     * @return face_image - 头像缩略图
     */
    public String getFaceImage() {
        return faceImage;
    }

    /**
     * 设置头像缩略图
     *
     * @param faceImage 头像缩略图
     */
    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage;
    }

    /**
     * 获取头像完整图
     *
     * @return face_image_big - 头像完整图
     */
    public String getFaceImageBig() {
        return faceImageBig;
    }

    /**
     * 设置头像完整图
     *
     * @param faceImageBig 头像完整图
     */
    public void setFaceImageBig(String faceImageBig) {
        this.faceImageBig = faceImageBig;
    }

    /**
     * 获取昵称
     *
     * @return nickname - 昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置昵称
     *
     * @param nickname 昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取二维码
     *
     * @return qrcode - 二维码
     */
    public String getQrcode() {
        return qrcode;
    }

    /**
     * 设置二维码
     *
     * @param qrcode 二维码
     */
    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    /**
     * 获取设备id
     *
     * @return cid - 设备id
     */
    public String getCid() {
        return cid;
    }

    /**
     * 设置设备id
     *
     * @param cid 设备id
     */
    public void setCid(String cid) {
        this.cid = cid;
    }
}