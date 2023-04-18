package com.hotel.app.validate.impl;

import com.hotel.app.service.CustomerService;
import com.hotel.app.validate.RegisterValidate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegisterValidateImpl implements RegisterValidate {
    private CustomerService customerService;
    @Override
    public String validPhoneNumber(String phoneNumber) {
        Boolean result = customerService.existPhoneNumber(phoneNumber);
        if (result) {
            return "Phone number already used";
        }
        return "true";
    }
    @Override
    public String validEmail(String email) {
        if (email.contains("@")) {
            Boolean result = customerService.existEmail(email);
            if (result) {
                return "Email already used";
            }
            return "true";
        }
        return "Invalid email";
    }
}
