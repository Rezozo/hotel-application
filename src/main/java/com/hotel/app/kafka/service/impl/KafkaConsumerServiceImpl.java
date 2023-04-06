package com.hotel.app.kafka.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.app.dto.BookingInfoDto;
import com.hotel.app.fcm.model.PushRequest;
import com.hotel.app.fcm.service.PushService;
import com.hotel.app.kafka.service.KafkaConsumerService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerServiceImpl implements KafkaConsumerService {
    private PushService pushService;
    private final ObjectMapper objectMapper;
    @Autowired
    public KafkaConsumerServiceImpl(PushService pushService, ObjectMapper objectMapper) {
        this.pushService = pushService;
        this.objectMapper = objectMapper;
    }
    @Override
    @KafkaListener(topics = "myTopic", groupId = "groupId")
    @SneakyThrows(value = JsonProcessingException.class)
    public void listener(String bookingInfoDtoJson) {
        BookingInfoDto bookingInfoDto = objectMapper.readValue(bookingInfoDtoJson, BookingInfoDto.class);
        String title = "New Booking";
        String message = "Arrival date: " + bookingInfoDto.getArrivalDate() + ", Departure date: " + bookingInfoDto.getDepartureDate() + ", Customer name: " + bookingInfoDto.getFullName();
        PushRequest pushRequest = new PushRequest(title, message, "1234567890");
        pushService.sendPushNotificationToToken(pushRequest);
    }
}
