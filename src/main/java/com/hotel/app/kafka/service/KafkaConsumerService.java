package com.hotel.app.kafka.service;


public interface KafkaConsumerService {
     void listener(String bookingInfoDtoJson);
}
