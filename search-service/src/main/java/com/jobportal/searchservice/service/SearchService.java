package com.jobportal.searchservice.service;

import com.jobportal.searchservice.dto.JobSearchResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class SearchService {

    private final RestTemplate restTemplate;

    public SearchService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // ✅ ENTRY METHOD (SAFE WRAPPER)
    public List<JobSearchResponse> searchJobs(String keyword, String location) {
        try {
            return fetchJobs(keyword, location);
        } catch (Exception ex) {
            return fallbackJobs(keyword, location, ex);
        }
    }

    // ✅ CIRCUIT BREAKER APPLIED HERE
    @CircuitBreaker(name = "jobService", fallbackMethod = "fallbackJobs")
    public List<JobSearchResponse> fetchJobs(String keyword, String location) {

        String url = "http://JOB-SERVICE/api/jobs";

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null || response.get("data") == null) {
            return List.of();
        }

        List<Map<String, Object>> jobList =
                (List<Map<String, Object>>) response.get("data");

        return jobList.stream()
                .filter(job -> {
                    String title = (String) job.get("title");
                    String loc = (String) job.get("location");
                    String status = (String) job.get("status");

                    return (status != null && status.equalsIgnoreCase("OPEN")) &&
                            (keyword == null || title.toLowerCase().contains(keyword.toLowerCase())) &&
                            (location == null || loc.toLowerCase().contains(location.toLowerCase()));
                })
                .map(job -> {
                    JobSearchResponse j = new JobSearchResponse();

                    j.setJobId((String) job.get("jobId"));
                    j.setTitle((String) job.get("title"));
                    j.setCompany((String) job.get("company"));
                    j.setLocation((String) job.get("location"));

                    return j;
                })
                .toList();
    }

    // ✅ FALLBACK METHOD (MUST MATCH SIGNATURE)
    public List<JobSearchResponse> fallbackJobs(String keyword, String location, Throwable ex) {
        System.out.println("🔥 FALLBACK TRIGGERED: " + ex.getMessage());
        return List.of();
    }
}