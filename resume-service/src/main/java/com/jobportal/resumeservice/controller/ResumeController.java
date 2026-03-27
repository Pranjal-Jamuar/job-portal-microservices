package com.jobportal.resumeservice.controller;

import com.jobportal.resumeservice.dto.ApiResponse;
import com.jobportal.resumeservice.entity.Resume;
import com.jobportal.resumeservice.service.ResumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private final ResumeService service;

    public ResumeController(ResumeService service) {
        this.service = service;
    }

    // UPLOAD
    @PostMapping
    public ResponseEntity<ApiResponse<Resume>> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") String userId) {

        Resume saved = service.upload(file, userId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Resume uploaded", saved)
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Resume>> getResume(@PathVariable String id) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Resume fetched", service.getResume(id))
        );
    }

    // GET BY USER
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Resume>>> getByUser(@PathVariable String userId) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "User resumes fetched", service.getByUser(userId))
        );
    }
}