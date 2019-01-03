package com.xiaao.wechat.netty;
/**
 * @author: Xia-ao
 * @create: 2018-12-21 10:51
 **/

import io.netty.channel.Channel;

import java.util.HashMap;

/**
 * @className: UserChannelRel
 * @description: 用户id 与channel 关联关系处理
 * @author: Xia-ao
 * @create: 2018-12-21 10:51
 **/
public class UserChannelRel {

    private static HashMap<String, Channel> manger = new HashMap<>();

    public static void put(String senderId, Channel channel){
        manger.put(senderId, channel);
    }

    public static Channel get(String senderId){
        return manger.get(senderId);
    }

    public static void output(){
        for (HashMap.Entry<String ,Channel> entry : manger.entrySet()){
            System.out.println("UserId: " + entry.getKey()
                    + ", ChannelId: " + entry.getValue().id().asLongText());
        }
    }
}
