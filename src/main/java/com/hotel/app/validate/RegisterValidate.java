package com.hotel.app.validate;

import org.springframework.http.ResponseEntity;

public interface RegisterValidate {
    ResponseEntity<String> validPhoneNumber(String phoneNumber);
    ResponseEntity<String> validEmail(String email);
}
