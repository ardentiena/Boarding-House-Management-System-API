package com.boardinghouse.boardinghouse_api.service;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.boardinghouse.boardinghouse_api.security.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // This filter will be registered in the security configuration to intercept requests and validate JWT tokens
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // The doFilterInternal method is where the JWT token will be extracted from the request, validated, and if valid, the authentication will be set in the security context
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        // Extract the JWT token from the Authorization header
        String authHeader = request.getHeader("Authorization");
        
        // Check if the Authorization header is present and starts with "Bearer "
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            // Extract the token from the header
            String token = authHeader.substring(7);
            // Validate the token using the JwtUtil class
            if (jwtUtil.validateToken(token)) {
                // If the token is valid, extract the username and set the authentication in the security context
                String username = jwtUtil.getUsernameFromToken(token);
                // Create a UserDetails object based on the username (you can customize this as needed)
                UserDetails userDetails = User.builder()
                        .username(username)
                        .password("") // Password is not needed for JWT authentication
                        .authorities(new ArrayList<>()) // No authorities for simplicity
                        .build();
                // Create an authentication token based on the user details
                UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                // Set the authentication in the security context 
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        // Continue the filter chain to allow the request to proceed to the next filter or the controller
        filterChain.doFilter(request, response);
    }
    
}
