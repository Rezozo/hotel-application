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
@CrossOrigin("http://localhost:8081/")
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
    public List<BookingInfoDto> allBookings(@RequestParam(required = false) String directionPrice, @RequestParam(required = false) String directionDate) {
        return bookingService.getAll(directionPrice, directionDate);
    }

    @RequestMapping(value = "/stats", method = RequestMethod.GET)
    public List<Map<String, Integer>> showStats(@RequestParam(required = false) Boolean thisMonth) {
        return bookingService.getStats(thisMonth);
    }

    @RequestMapping(value = "/customer/book", method = RequestMethod.GET)
    public List<BookingInfoDto> customersBookings(@RequestParam(required = false) String phoneNumber) {
        return bookingService.getByPhoneNumber(phoneNumber);
    }

    @RequestMapping(value = "/customer/book/{bookingId}", method = RequestMethod.DELETE)
    public void customersBookingsDelete(@PathVariable Integer bookingId) {
        bookingService.deleteById(bookingId);
    }

    @RequestMapping(value = "/customer/review", method = RequestMethod.GET)
    public ReviewInfoDto customersReview(@RequestParam(required = false) Integer id) {
        return reviewService.getByIdInfo(id);
    }
    
    @RequestMapping(value = "/customer/review/{reviewId}", method = RequestMethod.DELETE)
    public void customersReviewDelete(@PathVariable Integer reviewId) {
        reviewService.deleteById(reviewId);
    }
}
