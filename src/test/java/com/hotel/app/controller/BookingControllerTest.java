package com.hotel.app.controller;

import com.hotel.app.config.request.AuthenticationRequest;
import com.hotel.app.config.response.AuthenticationResponse;
import com.hotel.app.dto.BookingInfoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookingControllerTest {

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
    public void booking_Success() throws ParseException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date arrival = sdf.parse("2023-04-20");
        Date departure = sdf.parse("2023-04-25");
        BookingInfoDto bookingInfoDto = new BookingInfoDto(null, "Flerova Oksana Antonovna",
                "79239519735", "Luxury one", arrival, departure, null);

        HttpEntity<BookingInfoDto> requestEntity = new HttpEntity<>(bookingInfoDto, headers);
        ResponseEntity<String> response = restTemplate.exchange("/booking/", HttpMethod.POST, requestEntity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", response.getBody());
    }

    @Test
    public void booking_Fail_Occupied() throws ParseException {     // in database already exist 2023-05-01 - 2023-05-10
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date arrival = sdf.parse("2023-04-29");
        Date departure = sdf.parse("2023-05-11");
        BookingInfoDto bookingInfoDto = new BookingInfoDto(null, "Flerova Oksana Antonovna",
                "79239519735", "Family luxury", arrival, departure, null);

        HttpEntity<BookingInfoDto> requestEntity = new HttpEntity<>(bookingInfoDto, headers);
        ResponseEntity<String> response = restTemplate.exchange("/booking/", HttpMethod.POST, requestEntity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("In this interval the room is occupied", response.getBody());
    }
}
