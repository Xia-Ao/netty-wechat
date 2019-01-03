package com.xiaao.wechat.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "chat_msg")
public class ChatMsg  {
    /**
     * 用户聊天记录主键id
     */
    @Id
    private String id;

    /**
     * 发送方用户id
     */
    @Column(name = "send_user_id")
    private String sendUserId;

    /**
     * 接收方用户id
     */
    @Column(name = "accept_user_id")
    private String acceptUserId;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 接收读取状态
     */
    @Column(name = "sign_flag")
    private Integer signFlag;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 获取用户聊天记录主键id
     *
     * @return id - 用户聊天记录主键id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置用户聊天记录主键id
     *
     * @param id 用户聊天记录主键id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取发送方用户id
     *
     * @return send_user_id - 发送方用户id
     */
    public String getSendUserId() {
        return sendUserId;
    }

    /**
     * 设置发送方用户id
     *
     * @param sendUserId 发送方用户id
     */
    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    /**
     * 获取接收方用户id
     *
     * @return accept_user_id - 接收方用户id
     */
    public String getAcceptUserId() {
        return acceptUserId;
    }

    /**
     * 设置接收方用户id
     *
     * @param acceptUserId 接收方用户id
     */
    public void setAcceptUserId(String acceptUserId) {
        this.acceptUserId = acceptUserId;
    }

    /**
     * 获取消息内容
     *
     * @return msg - 消息内容
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置消息内容
     *
     * @param msg 消息内容
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 获取接收读取状态
     *
     * @return sign_flag - 接收读取状态
     */
    public Integer getSignFlag() {
        return signFlag;
    }

    /**
     * 设置接收读取状态
     *
     * @param signFlag 接收读取状态
     */
    public void setSignFlag(Integer signFlag) {
        this.signFlag = signFlag;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}