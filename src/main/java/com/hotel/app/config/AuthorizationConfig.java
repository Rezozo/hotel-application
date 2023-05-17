package com.hotel.app.config;

import com.hotel.app.config.filter.JwtAuthenticationFilter;
import com.hotel.app.repository.TokensRepository;
import com.hotel.app.repository.UsersRepository;
import com.hotel.app.service.*;
import com.hotel.app.service.impl.AuthenticationServiceImpl;
import com.hotel.app.service.impl.JwtServiceImpl;
import com.hotel.app.service.impl.TokensServiceImpl;
import com.hotel.app.service.impl.UsersServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AuthorizationConfig {
    @Bean
    public UsersServiceImpl usersService(UsersRepository usersRepository) {
        return new UsersServiceImpl(usersRepository);
    }
    @Bean
    public UserDetailsService userDetailsService(UsersRepository repository) {
        return username -> repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    @Bean
    public JwtServiceImpl jwtService(TokensService tokensService) {
        return new JwtServiceImpl(tokensService);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationConfig(JwtServiceImpl jwtService, UserDetailsService userDetailsService) {
        return new JwtAuthenticationFilter(jwtService,userDetailsService);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationService authenticationService(UsersService usersService,
                                                       PasswordEncoder passwordEncoder,
                                                       JwtService jwtService,
                                                       AuthenticationManager manager,
                                                       CustomerService customerService,
                                                       TokensService tokensService) {
        return new AuthenticationServiceImpl(usersService, passwordEncoder, jwtService, manager, customerService, tokensService);
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokensService tokensService(TokensRepository tokensRepository) {
        return new TokensServiceImpl(tokensRepository);
    }
}
