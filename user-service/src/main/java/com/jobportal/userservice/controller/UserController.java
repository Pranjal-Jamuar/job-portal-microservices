package com.jobportal.userservice.controller;

import com.jobportal.userservice.dto.UserResponse;
import com.jobportal.userservice.entity.User;
import com.jobportal.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jobportal.userservice.dto.LoginRequest;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest request) {

        User user = userService.login(request.getEmail(), request.getPassword());

        UserResponse response = new UserResponse();
        response.setUserId(user.getUserId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(String.valueOf(user.getRole()));

        return ResponseEntity.ok(user);
    }
}