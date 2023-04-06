package com.hotel.app.service.impl;

import com.hotel.app.config.request.AuthenticationRequest;
import com.hotel.app.config.request.RegisterRequest;
import com.hotel.app.config.response.AuthenticationResponse;
import com.hotel.app.enums.Role;
import com.hotel.app.models.Customer;
import com.hotel.app.models.Users;
import com.hotel.app.service.AuthenticationService;
import com.hotel.app.service.CustomerService;
import com.hotel.app.service.JwtService;
import com.hotel.app.service.UsersService;
import com.hotel.app.validate.RegisterValidate;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager manager;
    private final CustomerService customerService;
    private final RegisterValidate registerValidate;

    @Override
    public ResponseEntity<String> register(RegisterRequest request) {
        Users user = Users.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .groups(Role.User)
                .build();

        ResponseEntity<String> resPhone = registerValidate.validPhoneNumber(request.getPhoneNumber());
        ResponseEntity<String> resEmail = registerValidate.validEmail(request.getEmail());

        if (resEmail.equals(ResponseEntity.ok().build()) && resPhone.equals(ResponseEntity.ok().build())) {
            usersService.save(user);
            Customer customer = new Customer(usersService.getByEmail(request.getEmail()).getId(), request.getFullName(), request.getEmail(), request.getPhoneNumber());
            customerService.save(customer);
            return ResponseEntity.ok("Success");
        }

        if(!resEmail.equals(ResponseEntity.ok().build()))
            return resEmail;

        return resPhone;
    }

    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request, HttpServletResponse response) {
        try {
            manager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Users user = usersService.getByEmail(request.getEmail());
        String jwtToken = jwtService.generateToken(user);
        addTokenToCookie(response, jwtToken);
        return ResponseEntity.ok(AuthenticationResponse.builder()
                                    .token(jwtToken)
                                    .build());
    }
    @Override
    public void addTokenToCookie(HttpServletResponse response, String jwt) {
        Cookie cookie = new Cookie("token", jwt);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60 * 24 * 7);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
