package com.hotel.app.service.impl;

import com.hotel.app.config.request.AuthenticationRequest;
import com.hotel.app.config.request.RegisterRequest;
import com.hotel.app.config.response.AuthenticationResponse;
import com.hotel.app.enums.Role;
import com.hotel.app.models.Customer;
import com.hotel.app.models.Tokens;
import com.hotel.app.models.Users;
import com.hotel.app.service.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
    private final TokensService tokensService;
    @Override
    public void register(RegisterRequest request) throws BadCredentialsException {
        Users user = Users.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .groups(Role.User)
                .build();

        usersService.save(user);
        Customer customer = new Customer(usersService.getByEmail(request.getEmail()).getId(), request.getFullName(), request.getEmail(), request.getPhoneNumber());
        customerService.save(customer);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response) {
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
            tokensService.save(new Tokens(user.getId(), jwtToken, refreshToken));

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (BadCredentialsException e) {
            return null;
        }
    }
}
