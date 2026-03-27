package com.jobportal.analyticsservice.controller;

import com.jobportal.analyticsservice.entity.Analytics;
import com.jobportal.analyticsservice.service.AnalyticsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService service;

    public AnalyticsController(AnalyticsService service) {
        this.service = service;
    }

    // 🔹 CREATE EVENT
    @PostMapping
    public Analytics create(@RequestBody Analytics event) {
        return service.saveEvent(event);
    }

    // 🔹 GET ALL
    @GetMapping
    public List<Analytics> getAll() {
        return service.getAllEvents();
    }

    // 🔹 GET BY TYPE
    @GetMapping("/type/{type}")
    public List<Analytics> getByType(@PathVariable String type) {
        return service.getByType(type);
    }

    // 🔹 COUNT BY TYPE
    @GetMapping("/count/{type}")
    public long countByType(@PathVariable String type) {
        return service.countByType(type);
    }

    // 🔹 USER ACTIVITY
    @GetMapping("/user/{userId}")
    public List<Analytics> getByUser(@PathVariable String userId) {
        return service.getByUser(userId);
    }

    // 🔥 SUMMARY
    @GetMapping("/summary")
    public Map<String, Object> summary() {
        return service.getSummary();
    }
}