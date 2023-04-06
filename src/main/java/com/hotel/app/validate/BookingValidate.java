package com.hotel.app.validate;

import com.hotel.app.dto.BookingInfoDto;
import com.hotel.app.models.Customer;
import com.hotel.app.models.Room;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface BookingValidate {
    ResponseEntity<String> validBooking(BookingInfoDto bookingInfoDto, Customer customer, Room room, BindingResult bindingResult);
    Integer validCost(BookingInfoDto bookingInfoDto, Room room);
}
