package com.xiaao.wechat.netty;
/**
 * @author: Xia-ao
 * @create: 2018-12-21 11:36
 **/

import java.io.Serializable;

/**
 * @className: ChatMessage
 * @description: ChatMessage 序列化
 * @author: Xia-ao
 * @create: 2018-12-21 11:36
 **/
public class ChatMessage implements Serializable {

    private static final long serialVersionUID = 7121682128639119138L;

    private String senderId;		// 发送者的用户id
    private String receiverId;		// 接受者的用户id
    private String msg;				// 聊天内容
    private String msgId;			// 用于消息的签收

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
