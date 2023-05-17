package com.hotel.app.controller;

import com.hotel.app.config.request.AuthenticationRequest;
import com.hotel.app.config.request.RegisterRequest;
import com.hotel.app.config.response.AuthenticationResponse;
import com.hotel.app.service.AuthenticationService;
import com.hotel.app.service.JwtService;
import com.hotel.app.service.TokensService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:8081/")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final JwtService jwtService;
    private final TokensService tokensService;
    @RequestMapping(value = "/register", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request)
    {
        service.register(request);
        return ResponseEntity.ok("Success");
    }
    @RequestMapping(value = "/authenticate", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<Object> authenticate(@RequestBody AuthenticationRequest request, HttpServletResponse response)
    {
        AuthenticationResponse authenticationResponse = service.authenticate(request, response);
        if (authenticationResponse != null) {
            return ResponseEntity.ok(authenticationResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("ERROR");
        }
    }
    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public ResponseEntity<AuthenticationResponse> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        if (refreshToken == null || tokensService.getByRefreshToken(refreshToken) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String newToken = jwtService.generateTokenUseRefreshToken(refreshToken);
        tokensService.updateToken(newToken, refreshToken);
        return ResponseEntity.ok(AuthenticationResponse.builder()
                .token(newToken)
                .build());
    }
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity<?> logout (){
        return ResponseEntity.ok().body("Logout");
    }
}

