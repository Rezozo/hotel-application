package com.hotel.app.service;

import com.hotel.app.dto.ReviewInfoDto;
import com.hotel.app.models.Review;

import java.util.List;

public interface ReviewService {
    Review getById(Integer id);
    ReviewInfoDto getByEmail(String email);
    ReviewInfoDto getByPhoneNumber(String phoneNumber);
    List<ReviewInfoDto> getAll(String direction);
    void save(Review review);
    void updateRateAndFeedback(Integer id, Byte rate, String feedback);
    void deleteById(Integer id);
}
