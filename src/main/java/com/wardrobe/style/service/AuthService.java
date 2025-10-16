package com.wardrobe.style.service;

import com.wardrobe.style.dto.LoginRequest;
import com.wardrobe.style.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public String login(LoginRequest request) {
        try {
            // 1. Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            // 2. Get authenticated user details
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // 3. Generate JWT token
            return jwtService.generateToken(userDetails);

        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid email or password", e);
        }
    }
}
