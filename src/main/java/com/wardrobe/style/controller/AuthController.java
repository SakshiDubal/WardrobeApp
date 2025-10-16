package com.wardrobe.style.controller;

import com.wardrobe.style.dto.LoginRequest;
import com.wardrobe.style.dto.LoginResponse;
import com.wardrobe.style.dto.RegisterRequest;
import com.wardrobe.style.entity.User;
import com.wardrobe.style.repository.UserRepository;
import com.wardrobe.style.service.AuthService;
import com.wardrobe.style.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User user = userService.register(request);
            return ResponseEntity.ok("User registered");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        }
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(new LoginResponse(authService.login(request)));
    }

}
