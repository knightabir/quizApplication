package com.quiz.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    private static final String SECRET_KEY = "TaK+HaV^uvCHEFsEVfypW#7g9^k*Z8$V";

    private SecretKey getSigningKey() {
        try {
            return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        } catch (Exception e) {
            logger.error("Error generating signing key: ", e);
            throw new RuntimeException("Error generating signing key", e);
        }
    }

    public String extractUsername(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims.getSubject();
        } catch (Exception e) {
            logger.error("Error extracting username from token: ", e);
            throw new RuntimeException("Error extracting username", e);
        }
    }

    public Date extractExpiration(String token) {
        try {
            return extractAllClaims(token).getExpiration();
        } catch (Exception e) {
            logger.error("Error extracting expiration date from token: ", e);
            throw new RuntimeException("Error extracting expiration date", e);
        }
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            logger.warn("Token is expired: ", e);
            throw new RuntimeException("Token is expired", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token: ", e);
            throw new RuntimeException("Unsupported JWT token", e);
        } catch (Exception e) {
            logger.error("Error extracting claims from token: ", e);
            throw new RuntimeException("Error extracting claims from token", e);
        }
    }

    private Boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (Exception e) {
            logger.error("Error checking if token is expired: ", e);
            throw new RuntimeException("Error checking if token is expired", e);
        }
    }

    public String generateToken(String username) {
        try {
            Map<String, Object> claims = new HashMap<>();
            return createToken(claims, username);
        } catch (Exception e) {
            logger.error("Error generating token: ", e);
            throw new RuntimeException("Error generating token", e);
        }
    }

    private String createToken(Map<String, Object> claims, String subject) {
        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(subject)
                    .setHeaderParam("typ", "JWT")
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 50 * 20))
                    .signWith(getSigningKey())
                    .compact();
        } catch (Exception e) {
            logger.error("Error creating token: ", e);
            throw new RuntimeException("Error creating token", e);
        }
    }

    public Boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            logger.error("Error validating token: ", e);
            throw new RuntimeException("Error validating token", e);
        }
    }
}
