package com.hotel.app.config;

import com.hotel.app.repository.BookingRepository;
import com.hotel.app.service.BookingService;
import com.hotel.app.service.impl.BookingServiceImpl;
import com.hotel.app.validate.BookingValidate;
import com.hotel.app.validate.impl.BookingValidateImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = "com.hotel.app.models")
public class BookingConfig {
    @Bean
    public BookingService bookingService(BookingRepository bookingRepository) {
        return new BookingServiceImpl(bookingRepository);
    }
    @Bean
    public BookingValidate bookingValidate(BookingService bookingService) {
        return new BookingValidateImpl(bookingService);
    }
}
