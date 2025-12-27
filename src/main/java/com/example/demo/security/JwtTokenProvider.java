package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenProvider {
    
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    
    // Secret key should be at least 256 bits (32 characters)
    private final String secretKey = "VerySecretKeyForJwtDemoApplication123456";
    private final long expirationTime = 3600000L; // 1 hour
    private final Key key;
    
    public JwtTokenProvider() {
        try {
            byte[] keyBytes = this.secretKey.getBytes("UTF-8");
            if (keyBytes.length < 32) {
                throw new IllegalArgumentException("Secret key must be at least 32 bytes (256 bits)");
            }
            this.key = Keys.hmacShaKeyFor(keyBytes);
        } catch (UnsupportedEncodingException e) {
            logger.error("Failed to encode secret key: {}", e.getMessage());
            throw new RuntimeException("Failed to initialize JWT Key", e);
        }
    }

    /**
     * Generates a token based on username and role (Used in your AuthController)
     */
    public String generateToken(String username, String role) {
        try {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("Username cannot be null or empty");
            }
            
            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + expirationTime);
            
            return Jwts.builder()
                    .setSubject(username)
                    .claim("role", role)
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(key) // Modern JJWT automatically detects HS256
                    .compact();
        } catch (Exception e) {
            logger.error("Unexpected error generating token for user {}: {}", username, e.getMessage());
            throw new RuntimeException("Failed to generate token", e);
        }
    }
    
    /**
     * Generates a token based on Authentication object, userId and role
     */
    public String generateToken(Authentication auth, Long userId, String role) {
        try {
            String username = auth.getName();
            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + expirationTime);
            
            return Jwts.builder()
                    .setSubject(username)
                    .claim("userId", userId)
                    .claim("role", role)
                    .claim("email", username)
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(key) // Modern JJWT automatically detects HS256
                    .compact();
        } catch (Exception e) {
            logger.error("Unexpected error generating token for auth {}: {}", auth.getName(), e.getMessage());
            throw new RuntimeException("Failed to generate token", e);
        }
    }
    
    public boolean validateToken(String token) {
        try {
            if (token == null || token.trim().isEmpty()) {
                return false;
            }
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("JWT validation failed: {}", e.getMessage());
            return false;
        }
    }
    
    public String getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid token", e);
        }
    }
    
    public Map<String, Object> getAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid token", e);
        }
    }
}