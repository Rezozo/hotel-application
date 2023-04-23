package com.hotel.app.controller;

import com.hotel.app.dto.RoomInfoDto;
import com.hotel.app.models.RoomType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomeControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void homeTypes_Success(){
        ResponseEntity<List<RoomType>> response = restTemplate.exchange(
                "/myhotel/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});

        List<RoomType> roomTypes = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, roomTypes.size());
        assertEquals("Double",roomTypes.get(1).getTitle());
    }

    @Test
    public void allrooms_Success() {
        Boolean status = true;
        String direction = "ASC";

        ResponseEntity<List<RoomInfoDto>> response = restTemplate.exchange(
                "/myhotel/allrooms?status={status}&direction={direction}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {},
                status, direction
        );

        List<RoomInfoDto> roomInfoDto = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(20, roomInfoDto.get(0).getPrice());
        assertTrue(roomInfoDto.stream().anyMatch(dto -> "Luxury one bed".equals(dto.getTitle())));
    }

    @Test
    public void oneTypeRooms_Success() {
        String typetitle = "Double";
        String direction = "DESC";

        ResponseEntity<List<RoomInfoDto>> response = restTemplate.exchange(
                "/myhotel/{typetitle}/rooms?direction={direction}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {},
                typetitle, direction
        );

        List<RoomInfoDto> roomInfoDto = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Standard plus double with two beds", roomInfoDto.get(1).getTitle());
    }

    @Test
    public void oneRoom_Success() {
        String typetitle = "Quadruple";
        String roomTitle = "Family luxury";

        ResponseEntity<RoomInfoDto> response = restTemplate.exchange(
                "/myhotel/{typetitle}/rooms/{title}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {},
                typetitle, roomTitle
        );

        RoomInfoDto roomInfoOneDto = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(70, roomInfoOneDto.getPrice());
    }
}
