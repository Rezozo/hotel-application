package com.hotel.app.fcm.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import java.io.IOException

@Configuration
class FCMInitializer {
    @Value("\${google.firebase.adminsdk.json}")
    private lateinit var firebaseConfigPath: String

    val logger = LoggerFactory.getLogger(FCMInitializer::class.java)

    @Bean
    fun initialize() {
        try {
            val options : FirebaseOptions = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream( ClassPathResource(firebaseConfigPath).inputStream)).build()
            if(FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options)
                logger.info("Firebase application has been initialized")
            }
        } catch (e : IOException) {
            logger.error(e.message)
        }
    }
}