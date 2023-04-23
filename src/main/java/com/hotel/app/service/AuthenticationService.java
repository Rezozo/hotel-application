package com.hotel.app.service;

import com.hotel.app.config.request.AuthenticationRequest;
import com.hotel.app.config.request.RegisterRequest;
import com.hotel.app.config.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthenticationService {
    void register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response);
}
