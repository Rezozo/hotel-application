package com.hotel.app.fcm.service.impl;

import com.hotel.app.fcm.model.PushRequest;
import com.hotel.app.fcm.service.FCMService;
import com.hotel.app.fcm.service.PushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PushServiceImpl implements PushService {
    private Logger logger = LoggerFactory.getLogger(PushServiceImpl.class);
    private FCMService fcmService;
    public PushServiceImpl(FCMService fcmService) {
        this.fcmService = fcmService;
    }
    @Override
    public void sendPushNotificationToToken(PushRequest request) {
        try {
            fcmService.sendMessageToToken(request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
