package com.jobportal.resumeservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Resume {

    @Id
    private String resumeId;

    private String userId;
    private String filePath;
}