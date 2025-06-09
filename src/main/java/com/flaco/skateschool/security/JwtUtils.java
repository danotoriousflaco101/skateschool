package com.flaco.skateschool.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {
    @Value("${app.jwt.secret}")
    private String jwtSecret; // Secret key for signing JWT

    @Value("${app.jwt.expiration-ms}")
    private int jwtExpirationMs; // Token expiration time in ms

    // Generate JWT token from authentication object
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder().subject(userPrincipal.getUsername()) // Set username as subject
                .claim("roles", userPrincipal.getAuthorities()).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + jwtExpirationMs)) // Token expiry
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Sign with secret key
                .compact();
    }

    // Validate JWT token signature and expiry
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (SecurityException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    // Extract username (subject) from JWT token
    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey()) // Set secret key for parsing
                .build().parseSignedClaims(token).getPayload()
                .getSubject(); // Get subject (username)
    }

    // Get secret key for signing/parsing tokens
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
}