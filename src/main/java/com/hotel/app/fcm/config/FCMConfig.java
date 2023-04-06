package com.hotel.app.fcm.config;

import com.hotel.app.fcm.service.FCMService;
import com.hotel.app.fcm.service.PushService;
import com.hotel.app.fcm.service.impl.FCMServiceImpl;
import com.hotel.app.fcm.service.impl.PushServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FCMConfig {
    @Bean
    public FCMService fcmService() {
        return new FCMServiceImpl();
    }
    @Bean
    public PushService pushService(FCMService fcmService) {
        return new PushServiceImpl(fcmService);
    }
}
