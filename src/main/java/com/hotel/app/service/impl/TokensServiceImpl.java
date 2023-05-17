package com.hotel.app.service.impl;

import com.hotel.app.models.Tokens;
import com.hotel.app.repository.TokensRepository;
import com.hotel.app.service.TokensService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokensServiceImpl implements TokensService {
    private final TokensRepository tokensRepository;
    @Autowired
    public TokensServiceImpl(TokensRepository tokensRepository) {
        this.tokensRepository = tokensRepository;
    }

    @Override
    public Tokens getByRefreshToken(String refreshToken) {
        return tokensRepository.findByRefreshToken(refreshToken).orElse(null);
    }

    @Override
    public Tokens getByToken(String token) {
        return tokensRepository.findByToken(token).orElse(null);
    }

    @Override
    public void updateToken(String token, String refreshToken) {
        tokensRepository.findByRefreshToken(refreshToken).ifPresent(tokens -> {
            tokens.setToken(token);
            tokensRepository.save(tokens);
        });
    }

    @Override
    public void save(Tokens tokens) {
        tokensRepository.save(tokens);
    }

    @Override
    public void deleteById(Integer id) {
        tokensRepository.deleteById(id);
    }
}
