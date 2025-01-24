package com.snapit.interfaceadaptors.gateway.repositorydto;

import com.snapit.domain.entity.VideoProcessingStatus;

import java.time.LocalDateTime;

public class FrameProcessorRepositoryDTO {

    private final String id;
    private final String email;
    private final String originalFilename;
    private final Integer frameInterval;
    private final VideoProcessingStatus status;
    private final LocalDateTime createdAt;

    public FrameProcessorRepositoryDTO(String id, String email, String originalFilename, Integer frameInterval, VideoProcessingStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.originalFilename = originalFilename;
        this.frameInterval = frameInterval;
        this.status = status;
        this.createdAt = createdAt;
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

}
