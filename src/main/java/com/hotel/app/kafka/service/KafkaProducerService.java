package com.hotel.app.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hotel.app.dto.BookingInfoDto;

public interface KafkaProducerService {
    void sendMessage(BookingInfoDto bookingInfoDto) throws JsonProcessingException;
}
