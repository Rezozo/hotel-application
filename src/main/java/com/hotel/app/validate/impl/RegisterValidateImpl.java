package com.hotel.app.validate.impl;

import com.hotel.app.models.Customer;
import com.hotel.app.service.CustomerService;
import com.hotel.app.validate.RegisterValidate;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
public class RegisterValidateImpl implements RegisterValidate {
    private CustomerService customerService;
    @Override
    @SneakyThrows(value = NumberFormatException.class)
    public ResponseEntity<String> validPhoneNumber(String phoneNumber) {
        Long phone = Long.parseLong(phoneNumber);
        if (phone > 0 && phoneNumber.length() >= 11) {
            Customer customer = customerService.getByPhoneNumber(phoneNumber);
            if (customer != null) {
                return ResponseEntity.badRequest().body("Phone number already used");
            }
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Invalid phone number");
    }
    @Override
    public ResponseEntity<String> validEmail(String email) {
        if (email.contains("@")) {
            Customer customer = customerService.getByEmail(email);
            if (customer != null) {
                return ResponseEntity.badRequest().body("Email already used");
            }
            if (email.length() > 70) {
                return ResponseEntity.badRequest().body("Email is too long");
            }
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Invalid email");
    }
}
