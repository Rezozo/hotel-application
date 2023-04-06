package com.hotel.app.controller;

import com.hotel.app.dto.ReviewInfoDto;
import com.hotel.app.models.Customer;
import com.hotel.app.models.Review;
import com.hotel.app.service.CustomerService;
import com.hotel.app.service.ReviewService;
import com.hotel.app.validate.ReviewValidate;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@AllArgsConstructor
public class ReviewController {
    private ReviewService reviewService;
    private CustomerService customerService;
    private ReviewValidate reviewValidate;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<ReviewInfoDto> homeReviews(@RequestParam(required = false) String direction) {
        return reviewService.getAll(direction);
    }
    @RequestMapping(value = "/myReview", method = RequestMethod.GET)
    public ResponseEntity<ReviewInfoDto> myReview(@RequestBody String email) {
        ReviewInfoDto reviewInfoDto = reviewService.getByEmail(email);

        if (reviewInfoDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(reviewInfoDto);
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<String> addReview(@Valid @RequestBody ReviewInfoDto reviewDto, BindingResult bindingResult) {
        Customer customer = customerService.getByEmail(reviewDto.getEmail());
        ResponseEntity<String> responseEntity = reviewValidate.validReviewBeforeAdd(reviewDto, customer, bindingResult);

        if (responseEntity.equals(ResponseEntity.ok().build())) {
            Review review = new Review(customer.getId(), customer.getId(), reviewDto.getRate(), reviewDto.getFeedback());
            reviewService.save(review);
        }

        return responseEntity;
    }
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteReview(@RequestBody ReviewInfoDto reviewDto) {
        Customer customer = customerService.getByEmail(reviewDto.getEmail());

        if (customer == null) {
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        }

        reviewService.deleteById(customer.getId());
        return ResponseEntity.ok().build();
    }
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<String> updateReview(@Valid @RequestBody ReviewInfoDto reviewDto, BindingResult bindingResult) {
        Customer customer = customerService.getByEmail(reviewDto.getEmail());
        ResponseEntity<String> responseEntity = reviewValidate.validReviewBeforeUpdate(reviewDto, customer, bindingResult);

        if (responseEntity.equals(ResponseEntity.ok().build())) {
            reviewService.updateRateAndFeedback(customer.getId(), reviewDto.getRate(), reviewDto.getFeedback());
        }

        return responseEntity;
    }
}
