package com.hotel.app.repository;

import com.hotel.app.dto.ReviewInfoDto;
import com.hotel.app.models.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer> {
    @Query(value = "Select new com.hotel.app.dto.ReviewInfoDto(r.id, c.fullName, c.email, r.rate, r.feedback) " +
            "from Review r, Customer c where c.id = r.customer and c.email = :email")
    ReviewInfoDto findReviewInfoOne(@Param("email") String email);
    @Query(value = "Select new com.hotel.app.dto.ReviewInfoDto(r.id, c.fullName, c.email, r.rate, r.feedback) " +
            "from Review r, Customer c where c.id = r.customer and c.phoneNumber = :phoneNumber")
    ReviewInfoDto findReviewInfoOneByPhone(@Param("phoneNumber") String phoneNumber);
    @Query(value = "Select new com.hotel.app.dto.ReviewInfoDto(r.id, c.fullName, c.email, r.rate, r.feedback) " +
            "from Review r, Customer c where c.id = r.customer")
    List<ReviewInfoDto> findReviewInfoAll();
    @Query("SELECT new com.hotel.app.dto.ReviewInfoDto(r.id, c.fullName, c.email, r.rate, r.feedback) " +
            "FROM Review r, Customer c WHERE c.id = r.customer " +
            "ORDER BY CASE WHEN :direction = 'ASC' THEN r.rate END ASC, " +
            "CASE WHEN :direction = 'DESC' THEN r.rate END DESC")
    List<ReviewInfoDto> findReviewInfoAllOrderByRateDesc(@Param("direction") String direction);
}