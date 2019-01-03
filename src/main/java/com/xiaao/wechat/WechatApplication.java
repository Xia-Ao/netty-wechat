package com.xiaao.wechat;

import com.xiaao.wechat.utils.SpringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.xiaao.wechat.mapper")
// 扫描所有需要的包， 包含一些自用的工具类包所在的路径
@ComponentScan(basePackages = {"com.xiaao.wechat","org.n3r.idworker"})
public class WechatApplication {

    @Bean
    public SpringUtils getSpringUtils(){
        return  new SpringUtils();
    }

    public static void main(String[] args) {
        SpringApplication.run(WechatApplication.class, args);
    }
}

