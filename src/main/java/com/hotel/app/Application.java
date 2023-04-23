package com.hotel.app;

import com.hotel.app.config.*;

import com.hotel.app.fcm.config.FCMConfig;
import com.hotel.app.fcm.config.FCMInitializer;
import com.hotel.app.kafka.config.KafkaConsumerConfig;
import com.hotel.app.kafka.config.KafkaProducerConfig;
import com.hotel.app.kafka.config.KafkaTopicConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.hotel.app.repository")
@ComponentScan("com.hotel.app.controller")
@Import({ApplicationHomeConfig.class, AuthorizationConfig.class, SecurityConfig.class, ReviewConfig.class,
		BookingConfig.class, KafkaTopicConfig.class, KafkaProducerConfig.class, KafkaConsumerConfig.class,
		FCMConfig.class, FCMInitializer.class})
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
