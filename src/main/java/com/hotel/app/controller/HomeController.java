package com.hotel.app.controller;

import com.hotel.app.dto.RoomInfoDto;
import com.hotel.app.service.RoomService;
import com.hotel.app.service.RoomTypeService;
import com.hotel.app.models.RoomType;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/myhotel")
@CrossOrigin("http://localhost:8081/")
@AllArgsConstructor
public class HomeController {
    private RoomService roomService;
    private RoomTypeService roomTypeService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<RoomType> homeTypes() {
        return roomTypeService.getAll();
    }

    @RequestMapping(value = "/allrooms", method = RequestMethod.GET)
    public List<RoomInfoDto> homeRooms(@RequestParam(required = false)Boolean status, @RequestParam(required = false)String direction,
                                         @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false)Date arrivalDate,
                                         @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false)Date departureDate) {
        return roomService.getAll(status, direction, arrivalDate, departureDate);
    }
    @RequestMapping(value = "/{typetitle}/rooms", method = RequestMethod.GET)
    public ResponseEntity<List<RoomInfoDto>> homeOneType(@PathVariable String typetitle, @RequestParam(required = false)Boolean status,
                                                       @RequestParam(required = false)String direction,
                                                       @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false)Date arrivalDate,
                                                       @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false)Date departureDate) {
        RoomType roomType = roomTypeService.getByTitle(typetitle);

        if (roomType == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(roomService.getAllByType(roomType.getTitle(), status, direction, arrivalDate, departureDate));
    }

    @RequestMapping(value = "/{typetitle}/rooms/{title}", method = RequestMethod.GET)
    public ResponseEntity<RoomInfoDto> homeOneRoom(@PathVariable String typetitle, @PathVariable String title) {
        RoomType roomType = roomTypeService.getByTitle(typetitle);

        if (roomType == null) {
            return ResponseEntity.notFound().build();
        }

        RoomInfoDto roomsInfo = roomService.getByTitleAndType(typetitle, title);
        if (roomsInfo != null) return ResponseEntity.ok(roomsInfo);
        return ResponseEntity.badRequest().build();
    }
}
