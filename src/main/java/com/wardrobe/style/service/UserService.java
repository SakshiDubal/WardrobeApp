package com.wardrobe.style.service;

import com.wardrobe.style.dto.RegisterRequest;
import com.wardrobe.style.entity.User;
import com.wardrobe.style.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest request) {
        // 1. Check if user already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        // 2. Hash the password
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        // 3. Create User entity
        User user = User.builder()
                .email(request.getEmail())
                .displayName(request.getDisplayName())
                .password(hashedPassword) // maps to password_hash
                .preferences(Map.of())       // default empty JSON
                .build();

        // 4. Save user
        return userRepository.save(user);
    }
}
