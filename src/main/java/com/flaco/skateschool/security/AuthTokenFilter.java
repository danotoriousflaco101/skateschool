package com.flaco.skateschool.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService; // Loads user details from DB


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request); // Extract JWT token from request
            // Validate the token
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUsernameFromJwtToken(jwt);// Get username from token
                UserDetails userDetails = userDetailsService.loadUserByUsername(username); // Load user details
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()); // Create authentication object
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // Add request details
                SecurityContextHolder.getContext().setAuthentication(authentication); // Set authentication
            }
        } catch (Exception e) {
            logger.error("Brader i cannot set user authentication: {}", e); // Log error if auth fails
        }
        filterChain.doFilter(request, response); // Continue with the next filter
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization"); // Get Authorization header
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7); // Strip "Bearer " prefix and return token
        }
        return null; // No token found
    }
}