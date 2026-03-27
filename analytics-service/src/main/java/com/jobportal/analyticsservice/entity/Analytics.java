package com.jobportal.analyticsservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "analytics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Analytics {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String eventType;   // JOB_VIEW, APPLY
    private String userId;
    private String jobId;

    private LocalDateTime timestamp;
}