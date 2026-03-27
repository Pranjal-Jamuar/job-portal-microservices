package com.jobportal.resumeservice.repository;

import com.jobportal.resumeservice.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, String> {

    List<Resume> findByUserId(String userId);
}