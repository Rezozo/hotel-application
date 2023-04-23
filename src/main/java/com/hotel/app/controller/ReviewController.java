package com.hotel.app.controller;

import com.hotel.app.dto.ReviewInfoDto;
import com.hotel.app.exceptions.ReviewExistException;
import com.hotel.app.models.Customer;
import com.hotel.app.models.Review;
import com.hotel.app.service.CustomerService;
import com.hotel.app.service.ReviewService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "http://localhost:8081")
@AllArgsConstructor
public class ReviewController {
    private ReviewService reviewService;
    private CustomerService customerService;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<ReviewInfoDto> homeReviews(@RequestParam(required = false) String direction) {
        return reviewService.getAll(direction);
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<String> addReview(@Valid @RequestBody ReviewInfoDto reviewDto) {
        try {
            Customer customer = customerService.getByEmail(reviewDto.getEmail());
            reviewService.canReview(reviewDto, customer);

            Review review = new Review(customer.getId(), customer.getId(), reviewDto.getRate(), reviewDto.getFeedback());
            reviewService.save(review);
            return ResponseEntity.ok("Added");
        } catch (ReviewExistException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteReview(@RequestBody Review review) {
        Customer customer = customerService.getById(review.getId());
        reviewService.deleteById(customer.getId());
        return ResponseEntity.ok().build();
    }
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<String> updateReview(@Valid @RequestBody ReviewInfoDto reviewDto) {
        Customer customer = customerService.getById(reviewDto.getId());
        Boolean result = reviewService.canUpdate(reviewDto, customer);

        if (result) {
            reviewService.updateRateAndFeedback(customer.getId(), reviewDto.getRate(), reviewDto.getFeedback());
            return ResponseEntity.ok("Updated");
        }

        return ResponseEntity.badRequest().build();
    }
}
