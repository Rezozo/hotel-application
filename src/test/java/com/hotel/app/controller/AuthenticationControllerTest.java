package com.hotel.app.controller;

import com.hotel.app.config.request.AuthenticationRequest;
import com.hotel.app.config.request.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void register_Success() {
        RegisterRequest request = new RegisterRequest("Ivanov Ivan Ivanovich", "ivanov@gmail.com",
                "7967235321235", "1234");

        ResponseEntity<String> response = restTemplate.postForEntity("/auth/register", request, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", response.getBody());
    }

    @Test
    public void register_Fail_IncorrectEmail() {
        RegisterRequest request = new RegisterRequest("Ivanov Ivan Ivanovich", "ivanovgmail.com",
                "509329021213", "1234");

        ResponseEntity<String> response = restTemplate.postForEntity("/auth/register", request, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid email", response.getBody());
    }

    @Test
    public void authenticate_Success() {
        AuthenticationRequest request = new AuthenticationRequest("varvara53@mail.ru", "1234");

        ResponseEntity<String> response = restTemplate.postForEntity("/auth/authenticate", request, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void authenticate_Failed_IncorrectPassword() {
        AuthenticationRequest request = new AuthenticationRequest("varvara53@mail.ru", "12345");

        ResponseEntity<String> response = restTemplate.postForEntity("/auth/authenticate", request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
