package com.hotel.app.service.impl;

import com.hotel.app.dto.ReviewInfoDto;
import com.hotel.app.exceptions.ReviewExistException;
import com.hotel.app.models.Customer;
import com.hotel.app.service.ReviewService;
import com.hotel.app.models.Review;
import com.hotel.app.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }
    @Override
    public Review getById(Integer id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @Override
    public ReviewInfoDto getByIdInfo(Integer id) {
        return reviewRepository.findReviewInfoOneById(id);
    }
    @Override
    public ReviewInfoDto getByEmail(String email) {
        return reviewRepository.findReviewInfoOneByEmail(email);
    }
    @Override
    public List<ReviewInfoDto> getAll(String direction) {
        if (direction != null) {
            return reviewRepository.findReviewInfoAllOrderByRateDesc(direction);
        }
        return reviewRepository.findReviewInfoAll();
    }

    @Override
    public ReviewInfoDto getByPhoneNumber(String phoneNumber) {
        return reviewRepository.findReviewInfoOneByPhone(phoneNumber);
    }
    @Override
    public void save(Review review) {
        reviewRepository.save(review);
    }
    @Override
    public void deleteById(Integer id) {
        reviewRepository.deleteById(id);
    }
    @Override
    public void updateRateAndFeedback(Integer id, Byte rate, String feedback) {
        if (reviewRepository.existsById(id)) {
            Review review = reviewRepository.findById(id).get();
            review.setRate(rate);
            review.setFeedback(feedback);
            reviewRepository.save(review);
        }
    }
    @Override
    public Boolean canReview(ReviewInfoDto reviewDto, Customer customer) throws ReviewExistException {
        if(getById(customer.getId()) == null) {
            return true;
        } else {
            throw new ReviewExistException();
        }
    }
    @Override
    public Boolean canUpdate(ReviewInfoDto reviewDto, Customer customer) {
        if(getById(customer.getId()) != null) {
            return true;
        } else {
            return false;
        }
    }
}
