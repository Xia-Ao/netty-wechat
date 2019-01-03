package com.xiaao.wechat.enums;

/**
 * @author: Xia-ao
 * @create: 2018-12-21 10:42
 * @description: 消息签收状态枚举类型
 **/
public enum MsgActionEnum {

    CONNECT(1, "第一次(或重连)初始化连接"),
    CHAT(2, "聊天消息"),
    SIGNED(3, "消息签收"),
    KEEPALIVE(4, "客户端保持心跳"),
    PULL_FRIEND(5, "拉取好友");

    public final Integer type;
    public final String content;

    MsgActionEnum(Integer type, String content) {
        this.type = type;
        this.content = content;
    }

    public Integer getType(){
        return type;
    }
}
