package com.jobportal.jobservice.service;

import com.jobportal.jobservice.entity.Job;
import com.jobportal.jobservice.event.JobEventProducer;
import com.jobportal.jobservice.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final JobEventProducer producer;

    public JobService(JobRepository jobRepository, JobEventProducer producer) {
        this.jobRepository = jobRepository;
        this.producer = producer;
    }

    public Job createJob(Job job) {
        long count = jobRepository.count() + 1;
        job.setJobId("J" + (200 + count));
        job.setStatus("OPEN");

        Job saved = jobRepository.save(job);
        producer.sendJobCreated(saved);

        return saved;
    }

    public Job getJob(String jobId) {
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Job closeJob(String jobId) {
        Job job = getJob(jobId);
        job.setStatus("CLOSED");
        Job updated = jobRepository.save(job);

        producer.sendJobClosed(updated);

        return updated;
    }
}
