package com.spring.exercise;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

// Create and validate token
@Service
public class JwtService {

    //Store secret key in application.properties or env variable
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationMs;

    // Generate a JWT token for a user
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder().subject(userDetails.getUsername()).issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs)).signWith(getSigningKey()).compact();
    }

    //Extract the username (email) from a token
    public String extractUsername(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    // Check if token is valid
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);

        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();

        return claimsResolver.apply(claims);
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(this.secretKey.getBytes(StandardCharsets.UTF_8));
    }

    boolean isTokenValid(String token, org.springframework.security.core.userdetails.UserDetails userDetails) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
