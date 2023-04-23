package com.hotel.app.validate;

import com.hotel.app.dto.BookingInfoDto;
import com.hotel.app.exceptions.DatePastException;
import com.hotel.app.exceptions.RoomOccupiedException;
import com.hotel.app.models.Customer;
import com.hotel.app.models.Room;
import com.hotel.app.service.BookingService;
import com.hotel.app.validate.impl.BookingValidateImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingValidateTest {
    @InjectMocks
    BookingValidateImpl bookingValidate;
    @Mock
    BookingService bookingService;
    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(bookingValidate, "maxDays", 30);
    }
    @Test
    public void validBooking_Success() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date arrival = sdf.parse("2023-05-20");
        Date departure = sdf.parse("2023-05-25");
        BookingInfoDto bookingInfoDto = new BookingInfoDto(null, "Flerova Oksana Antonovna",
                "79239519735", "Luxury one", arrival, departure, null);

        Customer customer = new Customer(1, "Flerova Oksana Antonovna","oksana96@yandex.ru", "79239519735");

        Room room = new Room(1,1,100,"Luxury one", "Best room", "../img/LuxuryOne.png", 200, true);

        when(bookingService.getArrivalDates(room.getId())).thenReturn(new ArrayList<>());
        when(bookingService.getDepartureDates(room.getId())).thenReturn(new ArrayList<>());
        when(bookingService.canBook(bookingInfoDto, new ArrayList<>(), new ArrayList<>())).thenReturn(true);

        Boolean result = bookingValidate.validBooking(bookingInfoDto, customer, room);

        assertTrue(result);
        verify(bookingService).getArrivalDates(room.getId());
        verify(bookingService).getDepartureDates(room.getId());
        verify(bookingService).canBook(bookingInfoDto, new ArrayList<>(), new ArrayList<>());
    }

    @Test
    public void validBooking_Failed() {
        Exception exception = assertThrows(DatePastException.class, () -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date arrival = sdf.parse("2023-04-15");
            Date departure = sdf.parse("2023-04-25");
            BookingInfoDto bookingInfoDto = new BookingInfoDto(null, "Flerova Oksana Antonovna",
                    "79239519735", "Luxury one", arrival, departure, null);

            Customer customer = new Customer(1, "Flerova Oksana Antonovna","oksana96@yandex.ru", "79239519735");

            Room room = new Room(1,1,100,"Luxury one", "Best room", "../img/LuxuryOne.png", 200, true);

            when(bookingService.getArrivalDates(room.getId())).thenReturn(new ArrayList<>());
            when(bookingService.getDepartureDates(room.getId())).thenReturn(new ArrayList<>());
            when(bookingService.canBook(bookingInfoDto, new ArrayList<>(), new ArrayList<>())).thenReturn(true);

            bookingValidate.validBooking(bookingInfoDto, customer, room);
        });

        String expectedMessage = "Date is past";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
