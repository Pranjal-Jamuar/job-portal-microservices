package com.jobportal.applicationservice.controller;

import com.jobportal.applicationservice.dto.ApiResponse;
import com.jobportal.applicationservice.entity.Application;
import com.jobportal.applicationservice.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService service;

    public ApplicationController(ApplicationService service) {
        this.service = service;
    }

    // APPLY
    @PostMapping
    public ResponseEntity<ApiResponse<Application>> apply(@RequestBody Application app) {
        Application saved = service.applyJob(app);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Application submitted", saved)
        );
    }

    // GET BY JOB
    @GetMapping("/job/{jobId}")
    public ResponseEntity<ApiResponse<List<Application>>> getByJob(@PathVariable String jobId) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Applications fetched", service.getByJobId(jobId))
        );
    }

    // GET BY USER
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Application>>> getByUser(@PathVariable String userId) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Applications fetched", service.getByUserId(userId))
        );
    }

    // UPDATE STATUS
    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Application>> updateStatus(
            @PathVariable String id,
            @RequestBody Application request) {

        Application updated = service.updateStatus(id, request.getStatus());

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Status updated", updated)
        );
    }
}