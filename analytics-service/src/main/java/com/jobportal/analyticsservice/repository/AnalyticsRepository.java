package com.jobportal.analyticsservice.repository;

import com.jobportal.analyticsservice.entity.Analytics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnalyticsRepository extends JpaRepository<Analytics, String> {

    List<Analytics> findByEventType(String eventType);

    long countByEventType(String eventType);

    List<Analytics> findByUserId(String userId);
}