package com.hotel.app.repository;

import com.hotel.app.dto.BookingInfoDto;
import com.hotel.app.models.Booking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer> {
    @Query(value = "Select new com.hotel.app.dto.BookingInfoDto(b.id, c.fullName, c.phoneNumber, r.title as roomTitle, b.arrival, b.departure, b.cost)" +
            "from Booking b, Customer c, Room r where c.id = b.customer and r.id = b.room and " +
            "phoneNumber = :phoneNumber")
    List<BookingInfoDto> findBookingInfoOne(@Param("phoneNumber") String phoneNumber);

    @Query(value = "Select new com.hotel.app.dto.BookingInfoDto(b.id, c.fullName, c.phoneNumber, r.title as roomTitle, b.arrival, b.departure, b.cost)" +
            "from Booking b, Customer c, Room r where c.id = b.customer and r.id = b.room")
    List<BookingInfoDto> findBookingInfoAll();
    @Query(value = "Select new com.hotel.app.dto.BookingInfoDto(b.id, c.fullName, c.phoneNumber, r.title as roomTitle, b.arrival, b.departure, b.cost)" +
            "from Booking b, Customer c, Room r where c.id = b.customer and r.id = b.room " +
            "ORDER BY CASE WHEN :price = 'ASC' THEN b.cost END ASC, " +
            "CASE WHEN :price = 'DESC' THEN b.cost END DESC")
    List<BookingInfoDto> findBookingInfoAllOrderByPrice(@Param("price") String price);
    @Query(value = "Select new com.hotel.app.dto.BookingInfoDto(b.id, c.fullName, c.phoneNumber, r.title as roomTitle, b.arrival, b.departure, b.cost)" +
            "from Booking b, Customer c, Room r where c.id = b.customer and r.id = b.room " +
            "ORDER BY CASE WHEN :arrival = 'ASC' THEN b.arrival END ASC, " +
            "CASE WHEN :arrival = 'DESC' THEN b.arrival END DESC")
    List<BookingInfoDto> findBookingInfoAllOrderByArrival(@Param("arrival") String arrival);
    @Query(value = "SELECT booking.arrival_date " +
            "FROM booking " +
            "WHERE booking.room_id = ?1 and (booking.arrival_date > CURRENT_TIMESTAMP or booking.departure_date > CURRENT_TIMESTAMP) " +
            "Order by booking.id", nativeQuery = true)
    List<Timestamp> findBookingArrivalByRoomId(Integer roomId);
    @Query(value = "SELECT booking.departure_date " +
            "FROM booking " +
            "WHERE booking.room_id = ?1 and (booking.arrival_date > CURRENT_TIMESTAMP or booking.departure_date > CURRENT_TIMESTAMP) " +
            "Order by booking.id, booking.arrival_date", nativeQuery = true)
    List<Timestamp> findBookingDepartureByRoomId(Integer roomId);
    @Query(value = "Select count(id), sum(total_cost) " +
            "from booking", nativeQuery = true)
    List<Map<String, Integer>> findStatsBooking();

    @Query(value = "Select count(id), sum(total_cost) " +
            "from booking " +
            "WHERE EXTRACT(MONTH FROM arrival_date) = EXTRACT(MONTH FROM NOW()) " +
            "AND EXTRACT(YEAR FROM arrival_date) = EXTRACT(YEAR FROM NOW())", nativeQuery = true)
    List<Map<String, Integer>> findStatsBookingThisMonth();
}
