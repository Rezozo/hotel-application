package com.hotel.app.service;

import com.hotel.app.dto.BookingInfoDto;
import com.hotel.app.models.Booking;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface BookingService {
    List<BookingInfoDto> getByPhoneNumber(String phoneNumber);
    List<BookingInfoDto> getAll(String cost, String arrivalDate);
    List<Map<String, Integer>> getStats(Boolean thisMonth);
    List<Timestamp> getArrivalDates(Integer id);
    List<Timestamp> getDepartureDates(Integer id);
    void save(Booking booking);
    void deleteById(Integer id);
}