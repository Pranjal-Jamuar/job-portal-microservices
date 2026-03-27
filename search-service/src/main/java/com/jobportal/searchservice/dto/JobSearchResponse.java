package com.jobportal.searchservice.dto;

import lombok.Data;

@Data
public class JobSearchResponse {
    private String jobId;
    private String title;
    private String company;
    private String location;
}