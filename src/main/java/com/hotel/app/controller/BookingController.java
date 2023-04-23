package com.hotel.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hotel.app.dto.BookingInfoDto;
import com.hotel.app.exceptions.RoomOccupiedException;
import com.hotel.app.kafka.service.KafkaProducerService;
import com.hotel.app.models.Booking;
import com.hotel.app.models.Customer;
import com.hotel.app.models.Room;
import com.hotel.app.service.BookingService;
import com.hotel.app.service.CustomerService;
import com.hotel.app.service.RoomService;
import com.hotel.app.validate.BookingValidate;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
@CrossOrigin("http://localhost:8081/")
@AllArgsConstructor
public class BookingController {
    private CustomerService customerService;
    private RoomService roomService;
    private BookingService bookingService;
    private BookingValidate bookingValidate;
    private final KafkaProducerService producerService;
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    @SneakyThrows
    public ResponseEntity<?> roomBooking(@Valid @RequestBody(required = false) BookingInfoDto bookingInfoDto) {
        Customer customer = customerService.getByPhoneNumber(bookingInfoDto.getPhoneNumber());
        Room room = roomService.getByRoomTitle(bookingInfoDto.getRoomTitle());

        Boolean result = bookingValidate.validBooking(bookingInfoDto, customer, room);

        if (result) {
            Booking booking = new Booking(null, customer.getId(), room.getId(),
                    bookingInfoDto.getArrivalDate(), bookingInfoDto.getDepartureDate(), bookingService.getCost(bookingInfoDto, room));
            bookingService.save(booking);

            producerService.sendMessage(bookingInfoDto);
            return ResponseEntity.ok("Success");
        }

        return ResponseEntity.badRequest().build();
    }
}
