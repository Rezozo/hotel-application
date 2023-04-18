package com.hotel.app.config;

import com.hotel.app.handler.Handler;
import com.hotel.app.service.BookingService;
import com.hotel.app.service.RoomService;
import com.hotel.app.service.RoomTypeService;
import com.hotel.app.service.impl.RoomServiceImpl;
import com.hotel.app.service.impl.RoomTypeServiceImpl;
import com.hotel.app.repository.RoomRepository;
import com.hotel.app.repository.RoomTypeRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = "com.hotel.app.models")
public class ApplicationHomeConfig {
    @Bean
    public static RoomService roomService(RoomRepository roomRepository, BookingService  bookingService) {
        return new RoomServiceImpl(roomRepository,bookingService);
    }
    @Bean
    public static RoomTypeService roomTypeService(RoomTypeRepository roomTypeRepository) {
        return new RoomTypeServiceImpl(roomTypeRepository);
    }
    @Bean
    public static Handler handler() {
        return new Handler();
    }
}
