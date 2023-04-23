package com.hotel.app.fcm.service

import com.google.firebase.messaging.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hotel.app.fcm.model.PushRequest
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class FCMService {
    fun sendMessageToToken(request : PushRequest) {
        val message: Message = getPreconfiguredMessageToToken(request)
        val gson: Gson = GsonBuilder().setPrettyPrinting().create();
        val jsonOutPut: String = gson.toJson(message)
        val response: String = sendAndGetResponse(message)
    }

    private fun sendAndGetResponse(message: Message): String {
        return FirebaseMessaging.getInstance().sendAsync(message).get()
    }

    private fun getAndroidConfig(topic: String): AndroidConfig {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder()
                        .setTag(topic).build()).build()
    }

    private fun getApnsConfig(topic: String): ApnsConfig {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build()
    }

    private fun getPreconfiguredMessageToToken(request: PushRequest): Message {
        return getPreconfiguredMessageBuilder(request).setToken(request.token).build()
    }

    private fun getPreconfiguredMessageWithoutData(request: PushRequest): Message {
        return getPreconfiguredMessageBuilder(request).build()
    }

    private fun getPreconfiguredMessageWithData(data: Map<String, String>, request: PushRequest): Message {
        return getPreconfiguredMessageBuilder(request).putAllData(data).setToken(request.token).build()
    }

    private fun getPreconfiguredMessageBuilder(request: PushRequest): Message.Builder {
        val notificationBuilder: Notification.Builder = Notification.builder()
                .setTitle(request.title)
                .setBody(request.message)
        return Message.builder()
                .setNotification(notificationBuilder.build())
    }
}