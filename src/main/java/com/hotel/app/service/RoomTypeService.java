package com.hotel.app.service;

import com.hotel.app.models.RoomType;

import java.util.List;

public interface RoomTypeService {
    RoomType getById(Integer id);
    List<RoomType> getAll();
    void save(RoomType roomType);
    void deleteById(Integer id);
}
