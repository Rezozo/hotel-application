package com.hotel.app.validate;

import com.hotel.app.service.CustomerService;
import com.hotel.app.validate.impl.EmailValidatorImpl;
import com.hotel.app.validate.impl.PhoneNumberValidatorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegisterValidateTest {
    @InjectMocks
    EmailValidatorImpl emailValidator;
    @InjectMocks
    PhoneNumberValidatorImpl phoneNumberValidator;
    @Mock
    private CustomerService customerService;

    @Test
    public void validPhoneNumber_Success() {
        String phoneNumber = "792343412353";
        when(customerService.existPhoneNumber(phoneNumber)).thenReturn(false);

        boolean result = phoneNumberValidator.isValid(phoneNumber, null);

        assertTrue(result);
        verify(customerService).existPhoneNumber(phoneNumber);
    }

    @Test
    public void validPhoneNumber_Fail_PhoneNumberExists() {
        String phoneNumber = "79263193397";
        when(customerService.existPhoneNumber(phoneNumber)).thenReturn(true);

        boolean result = phoneNumberValidator.isValid(phoneNumber, null);
        assertFalse(result);
        verify(customerService).existPhoneNumber(phoneNumber);
    }

    @Test
    public void validEmail_Success() {
        String email = "krahmalev@yandex.ru";
        when(customerService.existEmail(email)).thenReturn(false);

        boolean result = emailValidator.isValid(email, null);

        assertTrue(result);
        verify(customerService).existEmail(email);
    }

    @Test
    public void validEmail_Fail_Invalid_email() {
        String email = "krahmalevyandex.ru";

        boolean result = emailValidator.isValid(email, null);

        assertFalse(result);
    }
}
