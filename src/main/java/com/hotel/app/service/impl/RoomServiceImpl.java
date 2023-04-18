package com.hotel.app.service.impl;

import com.hotel.app.dto.RoomInfoDto;
import com.hotel.app.service.BookingService;
import com.hotel.app.service.RoomService;
import com.hotel.app.models.Room;
import com.hotel.app.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;

import java.util.List;
@Service
public class RoomServiceImpl implements RoomService {
    private RoomRepository roomRepository;
    private BookingService bookingService;
    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository, BookingService bookingService) {
        this.roomRepository = roomRepository;
        this.bookingService = bookingService;
    }
    @Override
    public Room getByRoomTitle(String title) {
        return roomRepository.findByTitle(title)
                .orElseThrow(() -> new NoSuchElementException("Room not found with title: " + title));
    }
    @Override
    public List<RoomInfoDto> getAllByType(String type_title, Boolean status, String direction, Date arrivalDate, Date departureDate) {
        List<RoomInfoDto> resultList = new ArrayList<>();

        if (arrivalDate == null || departureDate == null) {
            if (direction == null) {
                return roomRepository.findRoomByTypeAndStatus(type_title, status);
            } else {
                return roomRepository.findRoomByTypeAndStatusOrderByPrice(type_title, status, direction);
            }
        }

        List<RoomInfoDto> roomInfoDto;
        if (direction == null) {
            roomInfoDto = roomRepository.findRoomByTypeAndStatus(type_title, status);
        } else {
            roomInfoDto = roomRepository.findRoomByTypeAndStatusOrderByPrice(type_title, status, direction);
        }

        for (RoomInfoDto room : roomInfoDto) {
            List<Date> arrivalDates = bookingService.getArrivalDates(room.getId());
            List<Date> departureDates = bookingService.getDepartureDates(room.getId());
            Boolean result = bookingService.canBookInThisSegment(arrivalDate, departureDate, arrivalDates, departureDates);
            if (result) {
                resultList.add(room);
            }
        }

        return resultList;
    }

    @Override
    public List<RoomInfoDto> getAll(Boolean status, String direction, Date arrivalDate, Date departureDate) {
        List<RoomInfoDto> resultList = new ArrayList<>();
        if(arrivalDate == null || departureDate == null) {
            if (direction == null) {
                return roomRepository.findRoomInfoAllByStatus(status);
            } else {
                return roomRepository.findRoomInfoAllByStatusOrderByPrice(status, direction);
            }
        }

        List<RoomInfoDto> roomInfoDto;

        if (direction == null) {
            roomInfoDto = roomRepository.findRoomInfoAllByStatus(status);
        } else {
            roomInfoDto = roomRepository.findRoomInfoAllByStatusOrderByPrice(status, direction);
        }

        for (RoomInfoDto room : roomInfoDto) {
            List<Date> arrivalDates = bookingService.getArrivalDates(room.getId());
            List<Date> departureDates = bookingService.getDepartureDates(room.getId());
            Boolean result = bookingService.canBookInThisSegment(arrivalDate, departureDate, arrivalDates, departureDates);
            if (result) {
                resultList.add(room);
            }
        }

        return resultList;
    }

    @Override
    public void save (Room room) {
        roomRepository.save(room);
    }

    @Override
    public void updateStatusById(Integer id) {
        if (roomRepository.existsById(id)) {
            Room room = roomRepository.findById(id).get();
            if (room.getStatus()) {
                room.setStatus(false);
                roomRepository.save(room);
            } else {
                room.setStatus(true);
                roomRepository.save(room);
            }
        }
    }
    @Override
    public void deleteById(Integer id) {
        roomRepository.deleteById(id);
    }
}
