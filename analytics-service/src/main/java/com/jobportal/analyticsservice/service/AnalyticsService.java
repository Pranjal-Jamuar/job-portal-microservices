package com.jobportal.analyticsservice.service;

import com.jobportal.analyticsservice.entity.Analytics;
import com.jobportal.analyticsservice.repository.AnalyticsRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsService {

    private final AnalyticsRepository repository;
    private final RestTemplate restTemplate;

    public AnalyticsService(AnalyticsRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    // 🔹 CREATE EVENT
    public Analytics saveEvent(Analytics event) {
        return repository.save(event);
    }

    // 🔹 GET ALL
    public List<Analytics> getAllEvents() {
        return repository.findAll();
    }

    // 🔹 GET BY TYPE
    public List<Analytics> getByType(String type) {
        return repository.findByEventType(type);
    }

    // 🔹 COUNT BY TYPE
    public long countByType(String type) {
        return repository.countByEventType(type);
    }

    // 🔹 GET BY USER
    public List<Analytics> getByUser(String userId) {
        return repository.findByUserId(userId);
    }

    // 🔥 SUMMARY (CROSS-SERVICE)
    public Map<String, Object> getSummary() {

        // Local analytics
        long totalEvents = repository.count();
        long jobViews = repository.countByEventType("JOB_VIEW");
        long applications = repository.countByEventType("APPLICATION_SUBMITTED");

        // USER SERVICE CALL
        Map userResponse = restTemplate.getForObject(
                "http://USER-SERVICE/api/users",
                Map.class
        );

        List users = (List) userResponse.get("data");
        int totalUsers = users.size();

        // JOB SERVICE CALL
        Map jobResponse = restTemplate.getForObject(
                "http://JOB-SERVICE/api/jobs",
                Map.class
        );

        List jobs = (List) jobResponse.get("data");
        int totalJobs = jobs.size();

        // Final response
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalUsers", totalUsers);
        summary.put("totalJobs", totalJobs);
        summary.put("totalEvents", totalEvents);
        summary.put("jobViews", jobViews);
        summary.put("applications", applications);

        return summary;
    }
}