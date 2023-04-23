package com.hotel.app.validate;

import com.hotel.app.dto.BookingInfoDto;
import com.hotel.app.exceptions.ArrivalException;
import com.hotel.app.exceptions.DatePastException;
import com.hotel.app.exceptions.RoomOccupiedException;
import com.hotel.app.models.Customer;
import com.hotel.app.models.Room;

public interface BookingValidate {
    Boolean validBooking(BookingInfoDto bookingInfoDto, Customer customer, Room room);
}
