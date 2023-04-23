package com.hotel.app.service.impl;

import com.hotel.app.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Configuration
public class JwtServiceImpl implements JwtService {
    @Value("${hotel.secret-key}")
    private String SECRET_KEY;

    @Value("${hotel.token.expiration}")
    private long tokenExpiration;

    @Value("${hotel.refreshtoken.expiration}")
    private String refreshTokenExpiration;
    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }
    @Override
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateTokenUseRefreshToken(String refreshToken) {
        final Claims claims = extractAllClaims(refreshToken);
        final String username = claims.getSubject();
        final Map<String, Object> extraClaims = new HashMap<>(claims);
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String refreshToken(String token) {
        final Claims claims = extractAllClaims(token);

        final String username = claims.getSubject();
        final Map<String, Object> extraClaims = new HashMap<>(claims);

        Instant now = Instant.now();
        Instant newExpirationInstant = now.plus(Period.parse(refreshTokenExpiration));;
        final Date newExpiration = Date.from(newExpirationInstant);

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(newExpiration)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    @Override
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserLogin(token);
        if (!username.equals(userDetails.getUsername())) {
            return false;
        }
        if (isTokenExpired(token)) {
            String newToken = refreshToken(token);
            token = newToken;
        }
        return !isTokenExpired(token);
    }
    @Override
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    @Override
    public String extractUserLogin(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    @Override
    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    @Override
    public Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
