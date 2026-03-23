package com.jobportal.jobservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "jobs")
public class Job {

    @Id
    private String jobId;

    private String title;

    private String company;

    private String location;

    private String salary;

    @Column(length = 1000)
    private String description;

    private String recruiterId;

    private String status; // OPEN / CLOSED
}