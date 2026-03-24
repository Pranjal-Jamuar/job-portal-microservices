package com.jobportal.applicationservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Application {

    @Id
    private String applicationId;

    private String jobId;
    private String userId;
    private String resumeLink;
    private String status;
}
