package com.snapit.interfaceadaptors.dto;

import com.snapit.domain.entity.VideoProcessingStatus;

public class FrameProcessorDTO {

    private String email;
    private String originalFilename;
    private VideoProcessingStatus status;

    public FrameProcessorDTO(String email, String originalFilename, VideoProcessingStatus status) {
        this.email = email;
        this.originalFilename = originalFilename;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public VideoProcessingStatus getStatus() {
        return status;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public void setStatus(VideoProcessingStatus status) {
        this.status = status;
    }
}
