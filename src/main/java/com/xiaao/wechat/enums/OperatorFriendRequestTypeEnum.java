package com.xiaao.wechat.enums;

/**
 * @author: Xia-ao
 * @create: 2018-12-09 23:27
 **/

/**
 * @Description: 忽略或者通过 好友请求的枚举
 **/
public enum OperatorFriendRequestTypeEnum {
    IGNORE(0, "忽略"),
    PASS(1, "通过");

    public final Integer type;
    public final String msg;

    OperatorFriendRequestTypeEnum(Integer type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public Integer getType() {
        return type;
    }

    public static String getMsgByType(Integer type) {
        for (OperatorFriendRequestTypeEnum opType : OperatorFriendRequestTypeEnum.values()) {
            if (opType.getType() == type) {
                return opType.msg;
            }
        }
        return null;
    }
}
