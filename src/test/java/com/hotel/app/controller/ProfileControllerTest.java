package com.hotel.app.controller;

import com.hotel.app.config.request.AuthenticationRequest;
import com.hotel.app.config.response.AuthenticationResponse;
import com.hotel.app.dto.BookingInfoDto;
import com.hotel.app.models.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProfileControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private String token;

    @BeforeEach
    public void authenticate() {
        AuthenticationRequest requestEntity = new AuthenticationRequest("oksana96@yandex.ru", "1234");
        ResponseEntity<AuthenticationResponse> responseEntity = restTemplate.postForEntity("/auth/authenticate", requestEntity, AuthenticationResponse.class);
        AuthenticationResponse responseBody = responseEntity.getBody();
        token = responseBody.getToken();
    }

    @Test
    public void myProfile_Success() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Customer> response = restTemplate.exchange(
                "/profile/?email={email}",
                HttpMethod.GET,
                requestEntity,
                Customer.class,
                "oksana96@yandex.ru"
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void booking_Success() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<List<BookingInfoDto>> response = restTemplate.exchange(
                "/profile/booking?email={email}",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<BookingInfoDto>>() {},
                "oksana96@yandex.ru"
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void bookingDelete_Success() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Customer> response = restTemplate.exchange(
                "/profile/booking/delete?id={id}",
                HttpMethod.DELETE,
                requestEntity,
                Customer.class,
                4
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
