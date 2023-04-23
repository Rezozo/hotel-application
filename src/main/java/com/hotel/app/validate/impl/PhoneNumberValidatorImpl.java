package com.hotel.app.validate.impl;

import com.hotel.app.service.CustomerService;
import com.hotel.app.validate.PhoneNumberValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PhoneNumberValidatorImpl implements ConstraintValidator<PhoneNumberValidator, String> {
    @Autowired
    private CustomerService customerService;
    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        if (phoneNumber.matches("[0-9]+")) {
            Boolean result = customerService.existPhoneNumber(phoneNumber);
            return !result;
        }
        return false;
    }
}
