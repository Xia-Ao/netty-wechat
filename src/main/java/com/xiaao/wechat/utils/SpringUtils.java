package com.xiaao.wechat.utils;
/**
 * @author: Xia-ao
 * @create: 2018-12-21 11:12
 **/

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @className: SpringUtils
 * @description: 提供手动获取 被spring 管理的bean对象
 * @author: Xia-ao
 * @create: 2018-12-21 11:12
 **/
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtils.applicationContext == null) {
            SpringUtils.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    // 通过name获取bean
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    // 通过class 获取bean
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    // 通过name class 返回指定的bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}
