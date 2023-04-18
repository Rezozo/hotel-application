package com.hotel.app.validate;

import com.hotel.app.service.CustomerService;
import com.hotel.app.validate.impl.RegisterValidateImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegisterValidateTest {
    @InjectMocks
    private RegisterValidateImpl registerValidate;

    @Mock
    private CustomerService customerService;

    @Test
    public void validPhoneNumber_Success() {
        String phoneNumber = "792343412353";
        when(customerService.existPhoneNumber(phoneNumber)).thenReturn(false);

        String result = registerValidate.validPhoneNumber(phoneNumber);

        assertEquals("true", result);
        verify(customerService).existPhoneNumber(phoneNumber);
    }

    @Test
    public void validPhoneNumber_Fail_PhoneNumberExists() {
        String phoneNumber = "79263193397";
        when(customerService.existPhoneNumber(phoneNumber)).thenReturn(true);

        String result = registerValidate.validPhoneNumber(phoneNumber);

        assertEquals("Phone number already used", result);
        verify(customerService).existPhoneNumber(phoneNumber);
    }

    @Test
    public void validEmail_Success() {
        String email = "krahmalev@yandex.ru";
        when(customerService.existEmail(email)).thenReturn(false);

        String result = registerValidate.validEmail(email);

        assertEquals("true", result);
        verify(customerService).existEmail(email);
    }

    @Test
    public void validEmail_Fail_Invalid_email() {
        String email = "krahmalevyandex.ru";

        String result = registerValidate.validEmail(email);

        assertEquals("Invalid email", result);
    }
}
