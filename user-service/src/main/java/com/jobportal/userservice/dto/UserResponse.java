package com.jobportal.userservice.dto;

import lombok.Data;

@Data
public class UserResponse {
    private String userId;
    private String name;
    private String email;
    private String role;
}