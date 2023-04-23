package com.hotel.app.fcm.config

import com.hotel.app.fcm.service.FCMService
import com.hotel.app.fcm.service.PushService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FCMConfig {
    @Bean
    fun fcmService(): FCMService {
        return FCMService()
    }
    @Bean
    fun pushService(fcmService: FCMService): PushService {
        return PushService(fcmService)
    }
}