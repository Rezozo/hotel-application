package com.hotel.app.repository;

import com.hotel.app.dto.RoomInfoDto;
import com.hotel.app.models.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {
    Optional<Room> findByTitle(String title);
    @Query("SELECT new com.hotel.app.dto.RoomInfoDto(r.id, t.title, r.number, r.title, r.description, r.image, r.price, r.status) " +
            "FROM Room r " +
            "Join RoomType t on r.type = t.id " +
            "WHERE (:status IS NULL OR r.status = :status) " +
            "Order by r.id ASC")
    List<RoomInfoDto> findRoomInfoAllByStatus(@Param("status") Boolean status);
    @Query("SELECT new com.hotel.app.dto.RoomInfoDto(r.id, t.title, r.number, r.title, r.description, r.image, r.price, r.status) " +
            "FROM Room r " +
            "Join RoomType t on r.type = t.id " +
            "WHERE (:status IS NULL OR r.status = :status) " +
            "ORDER BY CASE WHEN :direction = 'ASC' THEN r.price END ASC, " +
            "CASE WHEN :direction = 'DESC' THEN r.price END DESC")
    List<RoomInfoDto> findRoomInfoAllByStatusOrderByPrice(@Param("status") Boolean status, @Param("direction") String direction);
    @Query("SELECT new com.hotel.app.dto.RoomInfoDto(r.id, t.title, r.number, r.title, r.description, r.image, r.price, r.status) " +
            "FROM Room r " +
            "Join RoomType t on r.type = t.id " +
            "WHERE t.title = :title and (:status IS NULL OR r.status = :status) " +
            "Order by r.id ASC")
    List<RoomInfoDto> findRoomByTypeAndStatus(@Param("title") String type_title, @Param("status") Boolean status);
    @Query("SELECT new com.hotel.app.dto.RoomInfoDto(r.id, t.title, r.number, r.title, r.description, r.image, r.price, r.status) " +
            "FROM Room r " +
            "Join RoomType t on r.type = t.id " +
            "WHERE t.title = :title and (:status IS NULL OR r.status = :status) " +
            "ORDER BY CASE WHEN :direction = 'ASC' THEN r.price END ASC, " +
            "CASE WHEN :direction = 'DESC' THEN r.price END DESC")
    List<RoomInfoDto> findRoomByTypeAndStatusOrderByPrice(@Param("title") String type_title, @Param("status") Boolean status, @Param("direction") String direction);
}