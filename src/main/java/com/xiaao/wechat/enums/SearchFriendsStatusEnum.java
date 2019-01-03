package com.xiaao.wechat.enums;
/**
 * @author: Xia-ao
 * @create: 2018-12-06 11:27
 **/

/**
 * @className: SearchFriendsStatusEnum
 * @description: 添加好友查找结果枚举
 * @author: Xia-ao
 * @create: 2018-12-06 11:27
 **/
public enum SearchFriendsStatusEnum {

    SUCCESS(0, "OK"),
    USER_NOT_EXIST(1, "未找到任何结果！"),
    NOT_YOURSELF(2, "不能添加你自己！"),
    ALREADY_FRIENDS(3, "该用户已经是你的好友！");

    public final Integer status;
    public final String msg;

    SearchFriendsStatusEnum(Integer status,String msg){
        this.status= status;
        this.msg= msg;
    }

    public Integer getStatus(){
        return status;
    }

    public static String getMsgByKey(Integer status){
        for (SearchFriendsStatusEnum type: SearchFriendsStatusEnum.values()){
            if(type.getStatus()==status){
                return type.msg;
            }
        }
        return null;
    }
}
