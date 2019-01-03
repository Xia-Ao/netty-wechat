package com.xiaao.wechat.netty;
/**
 * @author: Xia-ao
 * @create: 2018-11-30 20:51
 **/

import com.xiaao.wechat.enums.MsgActionEnum;

import com.xiaao.wechat.service.UserService;
import com.xiaao.wechat.utils.JsonUtils;
import com.xiaao.wechat.utils.SpringUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: ChatHandler
 * @description: 自定义聊天handler
 * @author: Xia-ao
 * @create: 2018-11-30 20:51
 **/
// SimpleChannelInboundHandler: 对于请求来讲，其实相当于[入站，入境]
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // 用于记录和管理所有客户端的channle
    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

        // 获取客户端传过来的消息
        String content = msg.text();
        System.out.println("接收到的数据为：" + content);
        Channel currentChannel = ctx.channel();

        // 1. 获取客户端发来的消息
        DataContent dataContent = JsonUtils.jsonToPojo(content, DataContent.class);
        Integer action = dataContent.getAction();

        // 2. 判断消息类型，根据不同的消息类型来处理不同的业务类型
        if (action == MsgActionEnum.CONNECT.type) {
            // 2.1 当websocket第一次打开时，初始化channel， 吧channel和userid关联起来
            String senderId = dataContent.getChatMessage().getSenderId();
            // 关联channel 与userid
            UserChannelRel.put(senderId, currentChannel);

            // 测试结果
            UserChannelRel.output();

        } else if (action == MsgActionEnum.CHAT.type) {
            // 2.2 聊天类型消息，把聊天记录存到数据库，同时标记消息签收状态[未签收]
            ChatMessage chatMessage = dataContent.getChatMessage();
            String msgText = chatMessage.getMsg();
            String receiverId = chatMessage.getReceiverId();
            String senderId = chatMessage.getSenderId();

            // 保存数据到数据库，并标记为 [未签收]
            UserService userService = (UserService) SpringUtils.getBean("userServiceImpl");
            String msgId = userService.saveMsg(chatMessage);    // 保存聊天数据 并返回该条数据的msgId
            chatMessage.setMsgId(msgId);

            DataContent dataContentMsg = new DataContent();
            dataContentMsg.setChatMessage(chatMessage);

            // 发送消息
            // 从全局用户Channel关系中获取接受方的channel
            Channel receiverChannel = UserChannelRel.get(receiverId);
            if(receiverChannel == null){
                // TODO channel为空代表用户离线，推送消息（JPush，个推，小米推送）
            } else{
                // 当receiverChannel不为空的时候，从ChannelGroup去查找对应的channel是否存在
                Channel findChannel = clients.find(receiverChannel.id());
                if(findChannel!= null){
                    // 用户在线
                    receiverChannel.writeAndFlush( new TextWebSocketFrame(JsonUtils.objectToJson(dataContentMsg)));
                }else{
                    // 用户离线 推送消息
                }

            }


        } else if (action == MsgActionEnum.SIGNED.type) {
            // 2.3 签收消息类型，针对具体的消息进行签收，并修改数据库中消息签收状态 [已签收]
            UserService userService = (UserService) SpringUtils.getBean("userServiceImpl");
            // 扩展字段在signed 类型消息中，代表需要签收的消息id ， 逗号间隔
            String msgIdStr = dataContent.getExtand();
            String msgIds[]= msgIdStr.split(",");

            List<String> msgIdList = new ArrayList<>();
            for (String mid : msgIds){
                if(StringUtils.isBlank(mid)){
                    msgIdList.add(mid);
                }
            }

//            System.out.println(msgIdList.toString());

            // 批量签收
            if(msgIdList!= null && !msgIdList.isEmpty() && msgIdList.size()>0){
                userService.updateMsgSigned(msgIdList);
            }

        } else if (action == MsgActionEnum.KEEPALIVE.type) {
            // 2.4 心跳类型消息
            System.out.println("收到来自channel为[" + currentChannel + "]的心跳包...");
        }

//        clients.writeAndFlush(new TextWebSocketFrame("服务器在" + LocalDateTime.now()
//                + "时间，接收到消息：" + content));
    }

    /**
     * 当客户端连接服务端之后（打开连接）
     * 获取客户端的channle，并且放到ChannelGroup中去进行管理
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 当触发handlerRemoved，ChannelGroup会自动移除对应客户端的channel
//		clients.remove(ctx.channel());
        System.out.println("客户端断开，channle对应的长id为："
                + ctx.channel().id().asLongText());
        System.out.println("客户端断开，channle对应的短id为："
                + ctx.channel().id().asShortText());

    }

    /**
     * @Description: 异常操作
     **/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 发生异常之后 关闭连接， （关闭channel）， 随后从channelGroup中移除
        ctx.channel().close();
        clients.remove(ctx.channel());
    }
}
