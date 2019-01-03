package com.xiaao.wechat;
/**
 * @author: Xia-ao
 * @create: 2018-12-01 22:30
 **/

import com.xiaao.wechat.netty.WSServer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @className: NettyBooter
 * @description: NULL
 * @author: Xia-ao
 * @create: 2018-12-01 22:30
 **/
@Component
public class NettyBooter implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {

            try {
                WSServer.getInstance().start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
