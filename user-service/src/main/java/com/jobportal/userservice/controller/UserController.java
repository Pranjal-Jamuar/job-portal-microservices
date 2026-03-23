package com.jobportal.userservice.controller;

import com.jobportal.userservice.config.JwtUtil;
import com.jobportal.userservice.dto.ApiResponse;
import com.jobportal.userservice.dto.UserResponse;
import com.jobportal.userservice.entity.User;
import com.jobportal.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jobportal.userservice.dto.LoginRequest;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@RequestBody User user) {

        User savedUser = userService.registerUser(user);

        UserResponse response = new UserResponse();
        response.setUserId(savedUser.getUserId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());
        response.setRole(String.valueOf(savedUser.getRole()));

        return new ResponseEntity<>(
                new ApiResponse<>(true, "User registered successfully", response),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginRequest request) {

        User user = userService.login(request.getEmail(), request.getPassword());

        String token = jwtUtil.generateToken(user.getEmail());
//        UserResponse response = new UserResponse();
//        response.setUserId(user.getUserId());
//        response.setName(user.getName());
//        response.setEmail(user.getEmail());
//        response.setRole(String.valueOf(user.getRole()));

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Login successful", token)
        );
    }

//    @GetMapping("/test")
//    public String test() {
//        return "Protected API working";
//    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable String userId) {

        User user = userService.getUserById(userId);

        UserResponse response = new UserResponse();
        response.setUserId(user.getUserId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(String.valueOf(user.getRole()));

        return ResponseEntity.ok(
                new ApiResponse<>(true, "User fetched successfully", response)
        );
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable String userId,
            @RequestBody User user) {

        User updated = userService.updateUser(userId, user);

        UserResponse response = new UserResponse();
        response.setUserId(updated.getUserId());
        response.setName(updated.getName());
        response.setEmail(updated.getEmail());
        response.setRole(String.valueOf(updated.getRole()));

        return ResponseEntity.ok(
                new ApiResponse<>(true, "User updated successfully", response)
        );
    }
}