package com.hotel.app.controller;

import com.hotel.app.dto.BookingInfoDto;
import com.hotel.app.models.Customer;
import com.hotel.app.service.BookingService;
import com.hotel.app.service.CustomerService;
import com.hotel.app.service.UsersService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
@CrossOrigin("http://localhost:8081/")
@AllArgsConstructor
public class ProfileController {
    private CustomerService customerService;
    private UsersService usersService;
    private BookingService bookingService;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Customer myProfile(@RequestParam String email) {
        return customerService.getByEmail(email);
    }
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<String> updateCustomer(@Valid @RequestBody Customer customer) {
        customerService.updateCustomer(customer);
        usersService.updateById(customer.getId(), customer.getEmail(), customer.getFullName());
        return ResponseEntity.ok("Success");
    }
    @RequestMapping(value = "/booking", method = RequestMethod.GET)
    public List<BookingInfoDto> customersBookings(@RequestParam(required = false) String email) {
        Customer customer = customerService.getByEmail(email);
        return bookingService.getByPhoneNumber(customer.getPhoneNumber());
    }
    @RequestMapping(value = "/booking/delete", method = RequestMethod.DELETE)
    public void customersBookingsDelete(@RequestParam(required = false) Integer id) {
        bookingService.deleteById(id);
    }
}
