package com.jobportal.searchservice.controller;

import com.jobportal.searchservice.dto.JobSearchResponse;
import com.jobportal.searchservice.service.SearchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService service;

    public SearchController(SearchService service) {
        this.service = service;
    }

    @GetMapping("/jobs")
    public List<JobSearchResponse> searchJobs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location
    ) {
        return service.searchJobs(keyword, location);
    }
}