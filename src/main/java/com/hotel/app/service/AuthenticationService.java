package com.hotel.app.service;

import com.hotel.app.config.request.AuthenticationRequest;
import com.hotel.app.config.request.RegisterRequest;
import com.hotel.app.config.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthenticationService {
    String register(RegisterRequest request);
    Map<String, Object> authenticate(AuthenticationRequest request, HttpServletResponse response);
}
