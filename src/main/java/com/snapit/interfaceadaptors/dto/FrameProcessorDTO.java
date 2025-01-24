package com.snapit.interfaceadaptors.dto;

import com.snapit.domain.entity.VideoProcessingStatus;

public class FrameProcessorDTO {

    private final String id;

    private final String email;

    private final String originalFilename;

    private final VideoProcessingStatus status;

    public FrameProcessorDTO(String id, String email, String originalFilename, VideoProcessingStatus status) {
        this.id = id;
        this.email = email;
        this.originalFilename = originalFilename;
        this.status = status;
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

    public VideoProcessingStatus getStatus() {
        return status;
    }

}
