package com.hotel.app.fcm.service;

import com.hotel.app.fcm.model.PushRequest;

public interface PushService {
    void sendPushNotificationToToken(PushRequest request);
}
