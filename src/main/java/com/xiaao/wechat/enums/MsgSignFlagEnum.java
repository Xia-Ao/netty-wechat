package com.xiaao.wechat.enums;

/**
 * @author: Xia-ao
 * @create: 2018-12-21 14:34
 * @Description: 消息签收状态 枚举
 **/
public enum MsgSignFlagEnum {
    unsign(0, "未签收"),
    signed(1, "已签收");

    public final Integer type;
    public final String content;

    MsgSignFlagEnum(Integer type, String content){
        this.type = type;
        this.content = content;
    }

    public Integer getType() {
        return type;
    }
}
