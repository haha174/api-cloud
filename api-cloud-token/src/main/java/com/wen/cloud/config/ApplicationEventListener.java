package com.wen.cloud.config;


import com.wen.cloud.repository.RedisHandle;
import com.wen.cloud.util.BeanUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEventListener implements ApplicationListener<ApplicationReadyEvent> {
    @Value("user.name")
    private String username;
    @Value("user.pass")
    private String password;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        RedisHandle redis= BeanUtil.getBean("redisHandle");
        redis.set(username,password);
        System.out.println("LoginBean");
    }
}

