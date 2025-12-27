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
    private final String secretKey;
    private final long expirationTime;
    private final Key key;
    
    public JwtTokenProvider() {
        try {
            this.secretKey = "VerySecretKeyForJwtDemoApplication123456";
            
            long tempExpirationTime = 3600000L;
            if (tempExpirationTime <= 0 || tempExpirationTime > Long.MAX_VALUE / 2) {
                logger.error("Invalid expiration time: {}", tempExpirationTime);
                throw new IllegalArgumentException("Expiration time must be positive and within valid range");
            }
            this.expirationTime = tempExpirationTime;
            
            if (this.secretKey == null || this.secretKey.trim().isEmpty()) {
                logger.error("Secret key is null or empty");
                throw new IllegalArgumentException("Secret key cannot be null or empty");
            }
            
            byte[] keyBytes;
            try {
                keyBytes = this.secretKey.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("Failed to encode secret key: {}", e.getMessage());
                throw new IllegalArgumentException("Failed to encode secret key", e);
            }
            
            if (keyBytes.length < 32) {
                logger.error("Secret key too short: {} bytes", keyBytes.length);
                throw new IllegalArgumentException("Secret key must be at least 256 bits (32 bytes)");
            }
            
            this.key = Keys.hmacShaKeyFor(keyBytes);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error initializing JWT provider: {}", e.getMessage());
            throw new IllegalArgumentException("Failed to initialize JWT provider", e);
        }
    }
    
    public JwtTokenProvider(String secretKey, long expirationTime) {
        try {
            if (secretKey == null || secretKey.trim().isEmpty()) {
                logger.error("Secret key is null or empty in parameterized constructor");
                throw new IllegalArgumentException("Secret key cannot be null or empty");
            }
            if (expirationTime <= 0) {
                logger.error("Invalid expiration time in parameterized constructor: {}", expirationTime);
                throw new IllegalArgumentException("Expiration time must be positive");
            }
            
            this.secretKey = secretKey;
            this.expirationTime = expirationTime;
            
            byte[] keyBytes = secretKey.getBytes();
            if (keyBytes.length < 32) {
                logger.error("Secret key too short in parameterized constructor: {} bytes", keyBytes.length);
                throw new IllegalArgumentException("Secret key must be at least 256 bits (32 bytes)");
            }
            
            this.key = Keys.hmacShaKeyFor(keyBytes);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error in parameterized JWT provider constructor: {}", e.getMessage());
            throw new IllegalArgumentException("Failed to initialize JWT provider with parameters", e);
        }
    }
    
    public String generateToken(String username, String role) {
        try {
            if (username == null || username.trim().isEmpty()) {
                logger.error("Username is null or empty during token generation");
                throw new IllegalArgumentException("Username cannot be null or empty");
            }
            
            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + expirationTime);
            
            return Jwts.builder()
                    .setSubject(username)
                    .claim("role", role)
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error generating token for user {}: {}", username, e.getMessage());
            throw new IllegalArgumentException("Failed to generate token", e);
        }
    }
    
    public String generateToken(Authentication auth, Long userId, String role) {
        try {
            String username = auth.getName();
            if (username == null || username.trim().isEmpty()) {
                logger.error("Username is null or empty during token generation");
                throw new IllegalArgumentException("Username cannot be null or empty");
            }
            
            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + expirationTime);
            
            return Jwts.builder()
                    .setSubject(username)
                    .claim("userId", userId)
                    .claim("role", role)
                    .claim("email", username)
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error generating token for auth {}: {}", auth.getName(), e.getMessage());
            throw new IllegalArgumentException("Failed to generate token", e);
        }
    }
    
    public boolean validateToken(String token) {
        try {
            if (token == null || token.trim().isEmpty()) {
                logger.warn("Token is null or empty during validation");
                return false;
            }
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            logger.debug("JWT validation failed: {}", e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            logger.debug("Invalid argument during token validation: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            logger.error("Unexpected error during token validation: {}", e.getMessage());
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
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid token", e);
        }
    }
    
    public Map<String, Object> getAllClaims(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims;
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid token", e);
        }
    }
}