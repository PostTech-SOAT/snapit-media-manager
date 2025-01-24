package com.snapit.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class FrameProcessor {

    private final String id;

    private final String email;

    private final String originalFilename;

    private final Integer frameInterval;

    private final VideoProcessingStatus status;

    private final LocalDateTime createdAt;

    private final LocalDateTime finishedAt;

    private final String framesFilename;

    public FrameProcessor(String id, String email, String originalFilename, Integer frameInterval, VideoProcessingStatus status,
                          LocalDateTime createdAt, LocalDateTime finishedAt, String framesFilename) {
        this.id = id;
        this.email = email;
        this.originalFilename = originalFilename;
        this.frameInterval = frameInterval;
        this.status = status;
        this.createdAt = createdAt;
        this.finishedAt = finishedAt;
        this.framesFilename = framesFilename;
    }

    public FrameProcessor(String email, String originalFilename, Integer frameInterval, VideoProcessingStatus status,
                          LocalDateTime createdAt) {
        this.id = UUID.randomUUID().toString();
        this.email = email;
        this.originalFilename = originalFilename;
        this.frameInterval = frameInterval;
        this.status = status;
        this.createdAt = createdAt;
        this.finishedAt = null;
        this.framesFilename = null;
        frameValidation();
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public Integer getFrameInterval() {
        return frameInterval;
    }

    public VideoProcessingStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public String getFramesFilename() {
        return framesFilename;
    }

    private void frameValidation() {
        if (!emailValidation()) {
            throw new IllegalArgumentException("Invalid email on creating Frame Processor");
        }
        if (!frameIntervalValidation()) {
            throw new IllegalArgumentException("Invalid frame interval on creating Frame Processor, should be between 10 and 60");
        }
    }

    private boolean emailValidation() {
        return email != null && email.contains("@");
    }

    private boolean frameIntervalValidation() {
        return frameInterval != null && frameInterval >= 10 && frameInterval <= 60;
    }
}
