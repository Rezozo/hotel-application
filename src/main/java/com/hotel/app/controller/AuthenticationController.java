package com.hotel.app.controller;

import com.hotel.app.config.request.AuthenticationRequest;
import com.hotel.app.config.request.RegisterRequest;
import com.hotel.app.config.response.AuthenticationResponse;
import com.hotel.app.service.AuthenticationService;
import com.hotel.app.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final JwtService jwtService;
    @RequestMapping(value = "/register", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> register(@RequestBody RegisterRequest request)
    {
        return service.register(request);
    }

    @RequestMapping(value = "/authenticate", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request, HttpServletResponse response)
    {
        return service.authenticate(request, response);
    }
    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public ResponseEntity<AuthenticationResponse> refresh(@CookieValue("token") String token, HttpServletResponse response) {
        if (token == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String newToken = jwtService.refreshToken(token);
        service.addTokenToCookie(response, newToken);
        return ResponseEntity.ok(AuthenticationResponse.builder()
                .token(newToken)
                .build());
    }
}

