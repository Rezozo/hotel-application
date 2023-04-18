package com.hotel.app.kafka.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.app.dto.BookingInfoDto;
import com.hotel.app.kafka.service.KafkaProducerService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {
    private KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    @Override
    public void sendMessage(BookingInfoDto bookingInfoDto) throws JsonProcessingException {
        String bookingInfoDtoJson = objectMapper.writeValueAsString(bookingInfoDto);
        kafkaTemplate.send("myTopic", bookingInfoDtoJson);
    }
}
