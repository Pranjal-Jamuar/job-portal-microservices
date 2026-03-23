package com.jobportal.jobservice.controller;

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
    public ResponseEntity<?> createJob(@RequestBody Job job) {
        Job saved = jobService.createJob(job);

        return ResponseEntity.ok(Map.of(
                "jobId", saved.getJobId(),
                "message", "Job created"
        ));
    }

    @GetMapping("/{jobId}")
    public Job getJob(@PathVariable String jobId) {
        return jobService.getJob(jobId);
    }

    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    @PutMapping("/{jobId}/close")
    public Job closeJob(@PathVariable String jobId) {
        return jobService.closeJob(jobId);
    }
}
