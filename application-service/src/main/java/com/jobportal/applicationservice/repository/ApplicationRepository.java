package com.jobportal.applicationservice.repository;

import com.jobportal.applicationservice.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, String> {

    List<Application> findByJobId(String jobId);

    List<Application> findByUserId(String userId);
}