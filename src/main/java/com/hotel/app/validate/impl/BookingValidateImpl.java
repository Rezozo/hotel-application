package com.hotel.app.validate.impl;

import com.hotel.app.dto.BookingInfoDto;
import com.hotel.app.exceptions.ArrivalException;
import com.hotel.app.exceptions.DatePastException;
import com.hotel.app.exceptions.MaxPeriodException;
import com.hotel.app.exceptions.RoomOccupiedException;
import com.hotel.app.models.Customer;
import com.hotel.app.models.Room;
import com.hotel.app.service.BookingService;
import com.hotel.app.validate.BookingValidate;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookingValidateImpl implements BookingValidate {
    private BookingService bookingService;
    @Value("${hotel.booking.maxdays}")
    private int maxDays;
    @Autowired
    public BookingValidateImpl(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    @SneakyThrows
    @Override
    public Boolean validBooking(BookingInfoDto bookingInfoDto, Customer customer, Room room) {
        List<Date> arrivalDates = bookingService.getArrivalDates(room.getId());
        List<Date> departureDates = bookingService.getDepartureDates(room.getId());
        Boolean result = bookingService.canBook(bookingInfoDto, arrivalDates, departureDates);

        if (!result) {
            throw new RoomOccupiedException();
        }

        if (bookingInfoDto.getArrivalDate().after(bookingInfoDto.getDepartureDate())) {
            throw new ArrivalException();
        }

        Instant now = Instant.now();
        Instant arrivalInstant = bookingInfoDto.getArrivalDate().toInstant();

        if (now.isAfter(arrivalInstant)) {
            throw new DatePastException();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(bookingInfoDto.getArrivalDate().getTime());
        calendar.add(Calendar.DATE, maxDays);
        Timestamp maxDate = new Timestamp(calendar.getTimeInMillis());

        if (bookingInfoDto.getDepartureDate().after(maxDate)) {
            throw new MaxPeriodException();
        }

        return true;
    }
}
