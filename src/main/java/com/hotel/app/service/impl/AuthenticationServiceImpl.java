package com.hotel.app.service.impl;

import com.hotel.app.config.request.AuthenticationRequest;
import com.hotel.app.config.request.RegisterRequest;
import com.hotel.app.enums.Role;
import com.hotel.app.models.Customer;
import com.hotel.app.models.Users;
import com.hotel.app.service.AuthenticationService;
import com.hotel.app.service.CustomerService;
import com.hotel.app.service.JwtService;
import com.hotel.app.service.UsersService;
import com.hotel.app.validate.RegisterValidate;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
    public String register(RegisterRequest request) throws BadCredentialsException {
        Users user = Users.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .groups(Role.User)
                .build();

        String resPhone = registerValidate.validPhoneNumber(request.getPhoneNumber());
        String resEmail = registerValidate.validEmail(request.getEmail());

        if (resEmail.equals("true") && resPhone.equals("true")) {
            usersService.save(user);
            Customer customer = new Customer(usersService.getByEmail(request.getEmail()).getId(), request.getFullName(), request.getEmail(), request.getPhoneNumber());
            customerService.save(customer);
            return "Success";
        }

        if(!resEmail.equals("true"))
            return resEmail;

        return resPhone;
    }
    @Override
    public Map<String, Object> authenticate(AuthenticationRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();

        try {
            manager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            Users user = usersService.getByEmail(request.getEmail());
            String jwtToken = jwtService.generateToken(user);
            String refreshToken = jwtService.refreshToken(jwtToken);

            result.put("token", jwtToken);
            result.put("refreshToken", refreshToken);
        } catch (BadCredentialsException e) {
            result.put("error", "Invalid email or password");
        }

        return result;
    }
}
