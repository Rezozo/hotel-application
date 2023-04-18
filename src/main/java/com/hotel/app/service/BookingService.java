package com.hotel.app.service;

import com.hotel.app.dto.BookingInfoDto;
import com.hotel.app.models.Booking;
import com.hotel.app.models.Room;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BookingService {
    List<BookingInfoDto> getByPhoneNumber(String phoneNumber);
    List<BookingInfoDto> getAll(String cost, String arrivalDate);
    List<Map<String, Integer>> getStats(Boolean thisMonth);
    List<Date> getArrivalDates(Integer id);
    List<Date> getDepartureDates(Integer id);
    void save(Booking booking);
    void deleteById(Integer id);
    Boolean canBook(BookingInfoDto bookingInfoDto, List<Date> arrivalDates, List<Date> departureDates);
    Boolean canBookInThisSegment(Date arrivalDate, Date departureDate, List<Date> arrivalDates, List<Date> departureDates);
    Integer getCost(BookingInfoDto bookingInfoDto, Room room);
}