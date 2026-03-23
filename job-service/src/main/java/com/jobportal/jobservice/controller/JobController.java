package com.jobportal.jobservice.controller;

import com.jobportal.jobservice.dto.ApiResponse;
import com.jobportal.jobservice.entity.Job;
import com.jobportal.jobservice.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Job>> createJob(@RequestBody Job job) {
        Job saved = jobService.createJob(job);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Job created", saved)
        );
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<ApiResponse<Job>> getJob(@PathVariable String jobId) {
        Job job = jobService.getJob(jobId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Job fetched", job)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Job>>> getAllJobs() {
        List<Job> jobs = jobService.getAllJobs();

        return ResponseEntity.ok(
                new ApiResponse<>(true, "All jobs fetched", jobs)
        );
    }

    @PutMapping("/{jobId}/close")
    public ResponseEntity<ApiResponse<Job>> closeJob(@PathVariable String jobId) {
        Job job = jobService.closeJob(jobId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Job closed", job)
        );
    }
}
