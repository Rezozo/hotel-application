package com.hotel.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.app.dto.BookingInfoDto;
import com.hotel.app.kafka.MessageRequest;
import com.hotel.app.kafka.service.KafkaConsumerService;
import com.hotel.app.models.Booking;
import com.hotel.app.models.Customer;
import com.hotel.app.models.Room;
import com.hotel.app.service.BookingService;
import com.hotel.app.service.CustomerService;
import com.hotel.app.service.RoomService;
import com.hotel.app.validate.BookingValidate;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
@AllArgsConstructor
public class BookingController {
    private CustomerService customerService;
    private RoomService roomService;
    private BookingService bookingService;
    private BookingValidate bookingValidate;
    private KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> roomBooking(@RequestBody(required = false) BookingInfoDto bookingInfoDto, BindingResult bindingResult) throws JsonProcessingException {
        Customer customer = customerService.getByPhoneNumber(bookingInfoDto.getPhoneNumber());
        Room room = roomService.getByRoomTitle(bookingInfoDto.getRoomTitle());

        ResponseEntity<String> res = bookingValidate.validBooking(bookingInfoDto, customer, room, bindingResult);

        if(res.equals(ResponseEntity.ok().build())) {
            Booking booking = new Booking(null, customer.getId(), room.getId(),
                    bookingInfoDto.getArrivalDate(), bookingInfoDto.getDepartureDate(), bookingValidate.validCost(bookingInfoDto, room));
            bookingService.save(booking);
            MessageRequest messageRequest = new MessageRequest(bookingInfoDto);
            publish(messageRequest);
        }
        return res;
    }
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public void publish(@RequestBody MessageRequest request) throws JsonProcessingException {
        String bookingInfoDtoJson = objectMapper.writeValueAsString(request.bookingInfoDto());
        kafkaTemplate.send("myTopic", bookingInfoDtoJson);
    }
}
