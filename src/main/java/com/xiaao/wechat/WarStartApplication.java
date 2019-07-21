package com.xiaao.wechat;
/**
 * @author: Xia-ao
 * @create: 2019-01-03 15:31
 **/

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @className: WarStartApplication
 * @description:  使用外置tomcat启动WechatApplication
 * @author: Xia-ao
 * @create: 2019-01-03 15:31
 **/
public class WarStartApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WechatApplication.class);
    }
}
