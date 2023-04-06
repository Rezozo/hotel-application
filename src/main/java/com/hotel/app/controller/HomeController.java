package com.hotel.app.controller;

import com.hotel.app.dto.RoomInfoDto;
import com.hotel.app.dto.RoomInfoOneDto;
import com.hotel.app.service.BookingService;
import com.hotel.app.service.RoomService;
import com.hotel.app.service.RoomTypeService;
import com.hotel.app.models.RoomType;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/myhotel")
@AllArgsConstructor
public class HomeController {
    private RoomService roomService;
    private RoomTypeService roomTypeService;
    private BookingService bookingService;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<RoomType> homeTypes() {
        return roomTypeService.getAll();
    }

    @RequestMapping(value = "/allrooms", method = RequestMethod.GET)
    public List<RoomInfoDto> homeOneType(@RequestParam(required = false) Boolean status, @RequestParam(required = false) String direction) {
        return roomService.getAll(status, direction);
    }
    @RequestMapping(value = "/{id}/rooms", method = RequestMethod.GET)
    public ResponseEntity<List<RoomInfoDto>> homeRooms(@PathVariable Integer id, @RequestParam(required = false) Boolean status,
                                                       @RequestParam(required = false) String direction) {
        RoomType roomType = roomTypeService.getById(id);

        if (roomType == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(roomService.getAllByType(roomType.getTitle(), status, direction));
    }
    @RequestMapping(value = "/{id}/rooms/{title}", method = RequestMethod.GET)
    public ResponseEntity<RoomInfoOneDto> homeOneRoom(@PathVariable Integer id, @PathVariable String title) {
        RoomType roomType = roomTypeService.getById(id);
        if (roomType == null) {
            return ResponseEntity.notFound().build();
        }

        List<RoomInfoDto> roomsInfo = roomService.getAllByType(roomType.getTitle(), null, null);
        for (RoomInfoDto roomInfoDto : roomsInfo) {
            if (roomInfoDto.getTitle().equals(title)) {
                List<Timestamp> arrivalDates = bookingService.getArrivalDates(roomInfoDto.getId());
                List<Timestamp> departureDates = bookingService.getDepartureDates(roomInfoDto.getId());
                RoomInfoOneDto roomInfoOneDto = new RoomInfoOneDto(roomInfoDto.getId(), roomInfoDto.getType(), roomInfoDto.getNumber(), roomInfoDto.getTitle(), roomInfoDto.getDescription(),
                        roomInfoDto.getImage(), roomInfoDto.getPrice(), arrivalDates, departureDates, roomInfoDto.getStatus());
                return ResponseEntity.ok(roomInfoOneDto);
            }
        }

        return ResponseEntity.notFound().build();
    }
}
