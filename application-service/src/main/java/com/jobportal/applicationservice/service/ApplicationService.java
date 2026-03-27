package com.jobportal.applicationservice.service;

import com.jobportal.applicationservice.entity.Application;
import com.jobportal.applicationservice.event.ApplicationEventProducer;
import com.jobportal.applicationservice.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository repository;
    private final ApplicationEventProducer producer;

    public ApplicationService(ApplicationRepository repository, ApplicationEventProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    // APPLY
    public Application applyJob(Application app) {
        long count = repository.count() + 1;
        app.setApplicationId("A" + (300 + count));
        app.setStatus("APPLIED");

        Application saved = repository.save(app);

        producer.sendApplicationEvent(
                "User " + saved.getUserId() +
                        " applied for job " + saved.getJobId()
        );

        return saved;
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