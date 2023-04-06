package com.hotel.app.service.impl;

import com.hotel.app.dto.BookingInfoDto;
import com.hotel.app.service.BookingService;
import com.hotel.app.models.Booking;
import com.hotel.app.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookingServiceImpl implements BookingService {
    private BookingRepository bookingRepository;
    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }
    @Override
    public List<Timestamp> getArrivalDates(Integer id) {
        return bookingRepository.findBookingArrivalByRoomId(id);
    }
    @Override
    public List<Timestamp> getDepartureDates(Integer id) {
        return bookingRepository.findBookingDepartureByRoomId(id);
    }
    @Override
    public List<BookingInfoDto> getByPhoneNumber(String phoneNumber) {
        return bookingRepository.findBookingInfoOne(phoneNumber);
    }
    @Override
    public List<Map<String, Integer>> getStats(Boolean thisMonth) {
        if(thisMonth != null) {
            return bookingRepository.findStatsBookingThisMonth();
        }
        return bookingRepository.findStatsBooking();
    }

    @Override
    public List<BookingInfoDto> getAll(String cost, String arrivalDate) {
        if (cost == null && arrivalDate != null) {
            return bookingRepository.findBookingInfoAllOrderByArrival(arrivalDate);
        } else if (cost != null && arrivalDate == null) {
            return bookingRepository.findBookingInfoAllOrderByPrice(cost);
        }
        return bookingRepository.findBookingInfoAll();
    }

    @Override
    public void save(Booking booking) {
        bookingRepository.save(booking);
    }

    @Override
    public void deleteById(Integer id) {
        bookingRepository.deleteById(id);
    }
}
