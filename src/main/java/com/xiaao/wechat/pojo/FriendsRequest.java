package com.xiaao.wechat.pojo;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "friends_request")
public class FriendsRequest {
    /**
     * 好友消息发送表id
     */
    private String id;

    /**
     * 发送者id
     */
    @Column(name = "send_user_id")
    private String sendUserId;

    /**
     * 接收者id
     */
    @Column(name = "accept_user_id")
    private String acceptUserId;

    /**
     * 请求时间
     */
    @Column(name = "request_date_time")
    private Date requestDateTime;

    /**
     * 获取好友消息发送表id
     *
     * @return id - 好友消息发送表id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置好友消息发送表id
     *
     * @param id 好友消息发送表id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取发送者id
     *
     * @return send_user_id - 发送者id
     */
    public String getSendUserId() {
        return sendUserId;
    }

    /**
     * 设置发送者id
     *
     * @param sendUserId 发送者id
     */
    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    /**
     * 获取接收者id
     *
     * @return accept_user_id - 接收者id
     */
    public String getAcceptUserId() {
        return acceptUserId;
    }

    /**
     * 设置接收者id
     *
     * @param acceptUserId 接收者id
     */
    public void setAcceptUserId(String acceptUserId) {
        this.acceptUserId = acceptUserId;
    }

    /**
     * 获取请求时间
     *
     * @return request_date_time - 请求时间
     */
    public Date getRequestDateTime() {
        return requestDateTime;
    }

    /**
     * 设置请求时间
     *
     * @param requestDateTime 请求时间
     */
    public void setRequestDateTime(Date requestDateTime) {
        this.requestDateTime = requestDateTime;
    }
}