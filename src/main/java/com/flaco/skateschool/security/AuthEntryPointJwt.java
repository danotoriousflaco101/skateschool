package com.flaco.skateschool.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setContentType("application/json"); // Set response type
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Set 401 status
        response.getWriter().write("""
            {
                "error": "Unauthorized",
                "message": "Brader authentication failed: Invalid or missing credentials"
            }
            """); // Return custom error message
    }
}