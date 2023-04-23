package com.hotel.app.service;

import com.hotel.app.dto.RoomInfoDto;
import com.hotel.app.models.Room;

import java.util.Date;
import java.util.List;

public interface RoomService{
    Room getByRoomTitle(String title);
    RoomInfoDto getByTitleAndType(String typeTitle, String roomTitle);
    List<RoomInfoDto> getAllByType(String type_title, Boolean status, String direction, Date arrivalDate, Date departureDate);
    List<RoomInfoDto> getAll(Boolean status, String direction, Date arrivalDate, Date departureDate);
    void save(Room room);
    void updateStatusById(Integer id);
    void deleteById(Integer id);
}
