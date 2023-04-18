package com.hotel.app.repository;

import com.hotel.app.models.RoomType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomTypeRepository extends CrudRepository<RoomType, Integer> {
    Optional<RoomType> findByTitle(String title);
}