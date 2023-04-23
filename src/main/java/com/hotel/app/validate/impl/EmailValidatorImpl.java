package com.hotel.app.validate.impl;

import com.hotel.app.service.CustomerService;
import com.hotel.app.validate.EmailValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailValidatorImpl implements ConstraintValidator<EmailValidator, String> {
    @Autowired
    private CustomerService customerService;
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email.contains("@")) {
            Boolean result = customerService.existEmail(email);
            return !result;
        }
        return false;
    }
}
