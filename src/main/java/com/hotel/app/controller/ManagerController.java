package com.hotel.app.controller;

import com.hotel.app.dto.BookingInfoDto;
import com.hotel.app.dto.ReviewInfoDto;
import com.hotel.app.models.Customer;
import com.hotel.app.service.BookingService;
import com.hotel.app.service.CustomerService;
import com.hotel.app.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/manager")
@AllArgsConstructor
public class ManagerController {
    private CustomerService customerService;
    private BookingService bookingService;
    private ReviewService reviewService;
    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public List<Customer> allCustomers() {
        return customerService.getAll();
    }
    @RequestMapping(value = "/bookings", method = RequestMethod.GET)
    public List<BookingInfoDto> allBookings(@RequestParam(required = false) String dp, @RequestParam(required = false) String dd) {
        return bookingService.getAll(dp, dd);
    }
    @RequestMapping(value = "/stats", method = RequestMethod.GET)
    public List<Map<String, Integer>> showStats(@RequestParam(required = false) Boolean thisMonth) {
        return bookingService.getStats(thisMonth);
    }
    @RequestMapping(value = "/customer/book", method = RequestMethod.GET)
    public List<BookingInfoDto> customersBookings(@RequestParam(required = false) String pn) {
        return bookingService.getByPhoneNumber(pn);
    }
    @RequestMapping(value = "/customer/book/del", method = RequestMethod.DELETE)
    public void customersBookingsDelete(@RequestParam(required = false) Integer id) {
        bookingService.deleteById(id);
    }
    @RequestMapping(value = "/customer/review", method = RequestMethod.GET)
    public ReviewInfoDto customersReview(@RequestParam(required = false) String pn) {
        return reviewService.getByPhoneNumber(pn);
    }
    @RequestMapping(value = "/customer/review/del", method = RequestMethod.DELETE)
    public void customersReviewDelete(@RequestParam(required = false) Integer id) {
        reviewService.deleteById(id);
    }
}
