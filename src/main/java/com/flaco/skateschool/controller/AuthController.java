package com.flaco.skateschool.controller;

import com.flaco.skateschool.dto.LoginRequest;
import com.flaco.skateschool.dto.LoginResponse;
import com.flaco.skateschool.dto.SignupRequest;
import com.flaco.skateschool.exception.ErrorDetails;
import com.flaco.skateschool.security.JwtUtils;
import com.flaco.skateschool.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            return ResponseEntity.ok(new LoginResponse(jwt));
        } catch (AuthenticationException e) {
            logger.error("Authentication failed: {}", e.getMessage());
            ErrorDetails errorDetails = new ErrorDetails(
                    LocalDateTime.now(),
                    "Authentication failed",
                    e.getMessage(),
                    "AUTHENTICATION_FAILED",
                    "/api/auth/login"
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest request) {
        authService.registerUser(request);
        return ResponseEntity.ok("User registered successfully!");
    }
}