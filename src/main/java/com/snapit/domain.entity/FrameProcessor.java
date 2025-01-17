package com.snapit.domain.entity;

import java.time.LocalDateTime;

public class FrameProcessor {

    private String id;

    private String email;

    private final String originalFilename;

    private final String frameInterval;

    private final VideoProcessingStatus status;

    private final LocalDateTime createdAt;

    private final LocalDateTime finishedAt;

    private final String framesFilename;

    public FrameProcessor(String id, String email, String originalFilename, String frameInterval, VideoProcessingStatus status,
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

    public FrameProcessor(String email, String originalFilename, String frameInterval, VideoProcessingStatus status, LocalDateTime createdAt,
                          LocalDateTime finishedAt, String framesFilename) {
        this.email = email;
        this.originalFilename = originalFilename;
        this.frameInterval = frameInterval;
        this.status = status;
        this.createdAt = createdAt;
        this.finishedAt = finishedAt;
        this.framesFilename = framesFilename;
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

    public String getFrameInterval() {
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
}
