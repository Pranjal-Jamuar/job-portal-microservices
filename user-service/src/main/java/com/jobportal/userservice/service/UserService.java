package com.jobportal.userservice.service;

import com.jobportal.userservice.entity.User;
import com.jobportal.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {

        // Generate unique userId
        user.setUserId("U" + UUID.randomUUID().toString().substring(0, 5));

        return userRepository.save(user);
    }
}