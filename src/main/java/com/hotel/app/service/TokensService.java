package com.hotel.app.service;

import com.hotel.app.models.Tokens;

public interface TokensService {
    Tokens getByRefreshToken(String refreshToken);
    Tokens getByToken(String token);
    void updateToken(String token, String refreshToken);
    void save(Tokens tokens);
    void deleteById(Integer id);
}
