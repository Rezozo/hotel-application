package com.hotel.app.fcm.service;

import com.hotel.app.fcm.model.PushRequest;

import java.util.concurrent.ExecutionException;

public interface FCMService {
    void sendMessageToToken(PushRequest request)
            throws InterruptedException, ExecutionException;
}
