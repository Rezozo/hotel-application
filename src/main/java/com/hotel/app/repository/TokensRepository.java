package com.hotel.app.repository;

import com.hotel.app.models.Tokens;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokensRepository extends CrudRepository<Tokens, Integer> {
    Optional<Tokens> findByRefreshToken(String refreshToken);
    Optional<Tokens> findByToken(String token);
}
