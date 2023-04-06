package com.hotel.app.validate;

import com.hotel.app.dto.ReviewInfoDto;
import com.hotel.app.models.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface ReviewValidate {
    ResponseEntity<String> validReview(ReviewInfoDto reviewDto, Customer customer, BindingResult bindingResult);
    ResponseEntity<String> validReviewBeforeAdd(ReviewInfoDto reviewDto, Customer customer, BindingResult bindingResult);
    ResponseEntity<String> validReviewBeforeUpdate(ReviewInfoDto reviewDto, Customer customer, BindingResult bindingResult);
}
