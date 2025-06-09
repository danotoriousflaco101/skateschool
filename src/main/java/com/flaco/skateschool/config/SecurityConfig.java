package com.flaco.skateschool.config;

import com.flaco.skateschool.security.AuthEntryPointJwt;
import com.flaco.skateschool.security.AuthTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import java.util.List;

@Configuration
@EnableWebSecurity // Enables Spring Security
@EnableMethodSecurity // Enables method-level security (e.g. @PreAuthorize)
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthEntryPointJwt unauthorizedHandler; // Handles unauthorized access

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() { // Filters incoming requests
        return new AuthTokenFilter(); // Creates JWT filter
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // Configure security filter chain
        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration(); // CORS config
                    config.setAllowedOrigins(List.of("http://localhost:3000")); // Allow frontend
                    config.setAllowedMethods(List.of("*"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF (not needed for JWT)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Allow public auth endpoints
                        .requestMatchers("/error").permitAll() // Allow error path
                        .requestMatchers("/api/v1/users/me").authenticated() // Require auth for user info
                        .requestMatchers("/api/admin/**").hasRole("ADMIN") // Admin-only endpoints
                        .requestMatchers("/api/teacher/**").hasAnyRole("TEACHER", "ADMIN") // Teacher & Admin access
                        .anyRequest().authenticated() // All other endpoints require auth
                );

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class); // Add JWT filter
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
        // Auth manager bean
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        // Password encoder bean
    }
}

//this configuration sets up a secure, stateless, JWT-based authentication system with role-based access control for different parts of the API.