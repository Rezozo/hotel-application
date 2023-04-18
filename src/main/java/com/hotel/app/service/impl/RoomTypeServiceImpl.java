package com.hotel.app.service.impl;

import com.hotel.app.service.RoomTypeService;
import com.hotel.app.models.RoomType;
import com.hotel.app.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoomTypeServiceImpl implements RoomTypeService {
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    public RoomTypeServiceImpl(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    @Override
    public RoomType getById(Integer id) {
        return roomTypeRepository.findById(id).orElse(null);
    }

    @Override
    public RoomType getByTitle(String title) {
        return roomTypeRepository.findByTitle(title).orElse(null);
    }

    @Override
    public List<RoomType> getAll() {
        return (List<RoomType>) roomTypeRepository.findAll();
    }

    @Override
    public void save(RoomType roomType) {
        roomTypeRepository.save(roomType);
    }

    @Override
    public void deleteById(Integer id) {
        roomTypeRepository.deleteById(id);
    }
}
