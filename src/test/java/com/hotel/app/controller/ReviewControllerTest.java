package com.hotel.app.controller;

import com.hotel.app.config.request.AuthenticationRequest;
import com.hotel.app.config.response.AuthenticationResponse;
import com.hotel.app.dto.ReviewInfoDto;
import com.hotel.app.models.Review;
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
public class ReviewControllerTest {
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
    public void getAllReviews_Success() {
        ResponseEntity<List<ReviewInfoDto>> response = restTemplate.exchange(
                "/reviews/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ReviewInfoDto>>() {}
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void addReview_Success() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        ReviewInfoDto review = new ReviewInfoDto(null, "Flerova Oksana Antonovna", "oksana96@yandex.ru", (byte) 4, "Good hotel");
        HttpEntity<ReviewInfoDto> requestEntity = new HttpEntity<>(review, headers);

        ResponseEntity<String> response = restTemplate.exchange("/reviews/add", HttpMethod.POST, requestEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Added", response.getBody());
    }

    @Test
    public void addReview_Fail_Forbidden() {
        ReviewInfoDto review = new ReviewInfoDto(null, "Flerova Oksana Antonovna", "oksana96@yandex.ru", (byte) 4, "Good hotel");
        HttpEntity<ReviewInfoDto> requestEntity = new HttpEntity<>(review);

        ResponseEntity<String> response = restTemplate.exchange("/reviews/add", HttpMethod.POST, requestEntity, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void updateReview_Success() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        ReviewInfoDto review = new ReviewInfoDto(40, "Flerova Oksana Antonovna", "oksana96@yandex.ru", (byte) 2, "Bad hotel");
        HttpEntity<ReviewInfoDto> requestEntity = new HttpEntity<>(review, headers);

        ResponseEntity<String> response = restTemplate.exchange("/reviews/update", HttpMethod.PUT, requestEntity, String.class);
        System.out.println(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated", response.getBody());
    }

    @Test
    public void deleteReview_Success() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        Review review = new Review(40, 40, (byte) 2, "Bad hotel");
        HttpEntity<Review> requestEntity = new HttpEntity<>(review, headers);

        ResponseEntity<String> response = restTemplate.exchange("/reviews/delete", HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
