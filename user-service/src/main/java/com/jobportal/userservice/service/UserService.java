package com.jobportal.userservice.service;

import com.jobportal.userservice.entity.User;
import com.jobportal.userservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        // Generate unique userId
        long count = userRepository.count() + 1;
        user.setUserId("U" + (100 + count));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }

    public User getUserById(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(String userId, User updatedUser) {

        User existingUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());

        return userRepository.save(existingUser);
    }
}