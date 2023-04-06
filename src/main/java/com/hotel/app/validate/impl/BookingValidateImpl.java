package com.hotel.app.validate.impl;

import com.hotel.app.dto.BookingInfoDto;
import com.hotel.app.dto.RoomInfoOneDto;
import com.hotel.app.models.Customer;
import com.hotel.app.models.Room;
import com.hotel.app.service.BookingService;
import com.hotel.app.service.RoomService;
import com.hotel.app.validate.BookingValidate;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.List;

@AllArgsConstructor
public class BookingValidateImpl implements BookingValidate {
    private RoomService roomService;
    private BookingService bookingService;
    @Override
    public ResponseEntity<String> validBooking(BookingInfoDto bookingInfoDto, Customer customer, Room room, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {;
            return new ResponseEntity<>("One or more fields are empty or invalid", HttpStatus.BAD_REQUEST);
        }

        if (customer == null) {
            return ResponseEntity.badRequest().body("Phone number not found");
        }
        if (room == null) {
            return ResponseEntity.badRequest().body("Room not found");
        }

        List<Timestamp> arrivalDates = bookingService.getArrivalDates(room.getId());
        List<Timestamp> departureDates = bookingService.getDepartureDates(room.getId());

        for (int i = 0; i < arrivalDates.size(); i++) {
            if (!bookingInfoDto.getDepartureDate().before(arrivalDates.get(i))
                    && !bookingInfoDto.getArrivalDate().after(departureDates.get(i))) {
                return ResponseEntity.badRequest().body("In this interval the room is occupied");
            }
        }

        if (bookingInfoDto.getArrivalDate().after(bookingInfoDto.getDepartureDate())) {
            return ResponseEntity.badRequest().body("Arrival date cannot be bigger than date of departure");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(bookingInfoDto.getArrivalDate().getTime());
        calendar.add(Calendar.DATE, 30);
        Timestamp maxDate = new Timestamp(calendar.getTimeInMillis());

        if (bookingInfoDto.getDepartureDate().after(maxDate)) {
            return ResponseEntity.badRequest().body("Maximum booking period 30 days");
        }

        return ResponseEntity.ok().build();
    }

    @Override
    public Integer validCost(BookingInfoDto bookingInfoDto, Room room) {
        int costPerDay = room.getPrice();
        Timestamp arrivalDate = bookingInfoDto.getArrivalDate();
        Timestamp departureDate = bookingInfoDto.getDepartureDate();

        int millisecondsPerDay = 24 * 60 * 60 * 1000;
        long days = (departureDate.getTime() - arrivalDate.getTime()) / millisecondsPerDay;
        return (int) days * costPerDay;
    }
}
