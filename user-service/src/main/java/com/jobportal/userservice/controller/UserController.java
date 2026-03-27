package com.jobportal.userservice.controller;

import com.jobportal.userservice.config.JwtUtil;
import com.jobportal.userservice.dto.ApiResponse;
import com.jobportal.userservice.dto.UserResponse;
import com.jobportal.userservice.entity.User;
import com.jobportal.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jobportal.userservice.dto.LoginRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        // ✅ structured response
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("role", user.getRole().name());

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Login successful", response)
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

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers()
                .stream()
                .map(user -> {
                    UserResponse res = new UserResponse();
                    res.setUserId(user.getUserId());
                    res.setName(user.getName());
                    res.setEmail(user.getEmail());
                    res.setRole(user.getRole().name());
                    return res;
                })
                .toList();

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Users fetched", users)
        );
    }
}