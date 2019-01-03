package com.xiaao.wechat.netty;
/**
 * @author: Xia-ao
 * @create: 2018-12-21 10:31
 **/



import java.io.Serializable;

/**
 * @className: DataContent
 * @description: 聊天数据内容类
 * @author: Xia-ao
 * @create: 2018-12-21 10:31
 **/
public class DataContent implements Serializable {

    private static final long serialVersionUID = 5794517373157460328L;

    private Integer action;    // 动作类型
    private ChatMessage chatMessage;    // 用户的聊天内容
    private String extand;     // 扩展字段

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public ChatMessage getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
    }

    public String getExtand() {
        return extand;
    }

    public void setExtand(String extand) {
        this.extand = extand;
    }
}
