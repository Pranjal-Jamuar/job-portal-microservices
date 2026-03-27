package com.jobportal.resumeservice.service;

import com.jobportal.resumeservice.entity.Resume;
import com.jobportal.resumeservice.event.ResumeEventProducer;
import com.jobportal.resumeservice.repository.ResumeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ResumeService {

    private final ResumeRepository repository;
    private final ResumeEventProducer producer;

    public ResumeService(ResumeRepository repository, ResumeEventProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    // UPLOAD
    public Resume upload(MultipartFile file, String userId) {
        try {

            if (file.isEmpty()) {
                throw new RuntimeException("File is empty");
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            File dir = new File(uploadDir);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            Path path = Paths.get(uploadDir + fileName);
            Files.write(path, file.getBytes());

            Resume resume = new Resume();
            resume.setResumeId("R" + (100 + repository.count() + 1));
            resume.setUserId(userId);
            resume.setFilePath(path.toString());

            Resume saved = repository.save(resume);

            producer.sendResumeUploaded("Resume uploaded for user: " + resume.getUserId());

            return saved;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }

    // GET BY ID
    public Resume getResume(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resume not found"));
    }

    // GET BY USER
    public List<Resume> getByUser(String userId) {
        return repository.findByUserId(userId);
    }
}