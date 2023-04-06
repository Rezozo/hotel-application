package com.hotel.app.service.impl;

import com.hotel.app.dto.RoomInfoDto;
import com.hotel.app.service.RoomService;
import com.hotel.app.models.Room;
import com.hotel.app.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoomServiceImpl implements RoomService {
    private RoomRepository roomRepository;
    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    @Override
    public Room getByRoomTitle(String title) {
        return roomRepository.findByTitle(title).orElse(null);
    }
    @Override
    public List<RoomInfoDto> getAllByType(String type_title, Boolean status, String direction) {
        if (direction == null) {
            return roomRepository.findRoomByTypeAndStatus(type_title, status);
        } else {
            return roomRepository.findRoomByTypeAndStatusOrderByPrice(type_title, status, direction);
        }
    }
    @Override
    public List<RoomInfoDto> getAll(Boolean status, String direction) {
        if (direction == null) {
            return roomRepository.findRoomInfoAllByStatus(status);
        } else {
            return roomRepository.findRoomInfoAllByStatusOrderByPrice(status, direction);
        }
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
