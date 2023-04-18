package com.hotel.app.config;

import com.hotel.app.repository.CustomerRepository;
import com.hotel.app.repository.ReviewRepository;
import com.hotel.app.service.CustomerService;
import com.hotel.app.service.ReviewService;
import com.hotel.app.service.impl.CustomerServiceImpl;
import com.hotel.app.service.impl.ReviewServiceImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = "com.hotel.app.models")
public class ReviewConfig {
    @Bean
    public static ReviewService reviewService(ReviewRepository reviewRepository) {
        return new ReviewServiceImpl(reviewRepository);
    }
    @Bean
    public static CustomerService customerService(CustomerRepository customerRepository) {
        return new CustomerServiceImpl(customerRepository);
    }
}
