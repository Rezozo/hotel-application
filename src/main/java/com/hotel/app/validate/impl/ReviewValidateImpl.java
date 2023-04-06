package com.hotel.app.validate.impl;

import com.hotel.app.dto.ReviewInfoDto;
import com.hotel.app.models.Customer;
import com.hotel.app.service.CustomerService;
import com.hotel.app.service.ReviewService;
import com.hotel.app.validate.ReviewValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public class ReviewValidateImpl implements ReviewValidate {
    private CustomerService customerService;
    private ReviewService reviewService;
    @Autowired
    public ReviewValidateImpl(CustomerService customerService, ReviewService reviewService) {
        this.customerService = customerService;
        this.reviewService = reviewService;
    }
    @Override
    public ResponseEntity<String> validReview(ReviewInfoDto reviewDto, Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {;
            return new ResponseEntity<>("One or more fields are empty or invalid", HttpStatus.BAD_REQUEST);
        }

        if (customer == null) {
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        }

        if(reviewDto.getRate() > 5 || reviewDto.getRate() < 1) {
            return new ResponseEntity<>("Rating must be from 1 to 5", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().build();
    }
    @Override
    public ResponseEntity<String> validReviewBeforeAdd(ReviewInfoDto reviewDto, Customer customer, BindingResult bindingResult) {
        ResponseEntity<String> validate = validReview(reviewDto, customer, bindingResult);
        if(validate.equals(ResponseEntity.ok().build())) {
            if(reviewService.getById(customer.getId()) == null) {
                return ResponseEntity.ok().build();
            } else {
                return new ResponseEntity<>("Review already exists", HttpStatus.BAD_REQUEST);
            }
        }
        return validate;
    }
    @Override
    public ResponseEntity<String> validReviewBeforeUpdate(ReviewInfoDto reviewDto, Customer customer, BindingResult bindingResult) {
        ResponseEntity<String> validate = validReview(reviewDto, customer, bindingResult);
        if(validate.equals(ResponseEntity.ok().build())) {
            if(reviewService.getById(customer.getId()) != null) {
                return ResponseEntity.ok().build();
            } else {
                return new ResponseEntity<>("Review not exists", HttpStatus.BAD_REQUEST);
            }
        }
        return validate;
    }
}
