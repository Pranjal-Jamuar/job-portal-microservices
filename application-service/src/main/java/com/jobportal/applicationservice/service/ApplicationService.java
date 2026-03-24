package com.jobportal.applicationservice.service;

import com.jobportal.applicationservice.entity.Application;
import com.jobportal.applicationservice.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository repository;

    public ApplicationService(ApplicationRepository repository) {
        this.repository = repository;
    }

    // APPLY
    public Application applyJob(Application app) {
        long count = repository.count() + 1;
        app.setApplicationId("A" + (300 + count));
        app.setStatus("APPLIED");

        return repository.save(app);
    }

    // GET BY JOB
    public List<Application> getByJobId(String jobId) {
        return repository.findByJobId(jobId);
    }

    // GET BY USER
    public List<Application> getByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    // UPDATE STATUS
    public Application updateStatus(String id, String status) {
        Application app = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setStatus(status);
        return repository.save(app);
    }
}