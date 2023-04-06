package com.hotel.app.config;

import com.hotel.app.service.ReviewService;
import com.hotel.app.service.RoomService;
import com.hotel.app.service.RoomTypeService;
import com.hotel.app.service.impl.ReviewServiceImpl;
import com.hotel.app.service.impl.RoomServiceImpl;
import com.hotel.app.service.impl.RoomTypeServiceImpl;
import com.hotel.app.repository.ReviewRepository;
import com.hotel.app.repository.RoomRepository;
import com.hotel.app.repository.RoomTypeRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.hotel.app.models")
public class ApplicationHomeConfig {
    @Bean
    public static RoomService roomService(RoomRepository roomRepository) {
        return new RoomServiceImpl(roomRepository);
    }
    @Bean
    public static RoomTypeService roomTypeService(RoomTypeRepository roomTypeRepository) {
        return new RoomTypeServiceImpl(roomTypeRepository);
    }
}
