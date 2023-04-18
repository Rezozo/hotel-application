package com.hotel.app.validate;

import com.hotel.app.dto.BookingInfoDto;
import com.hotel.app.models.Customer;
import com.hotel.app.models.Room;

public interface BookingValidate {
    String validBooking(BookingInfoDto bookingInfoDto, Customer customer, Room room);
}
