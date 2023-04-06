package com.hotel.app.kafka;

import com.hotel.app.dto.BookingInfoDto;

public record MessageRequest(BookingInfoDto bookingInfoDto) {
}

