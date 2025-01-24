package com.snapit.framework.entity;

import com.snapit.domain.entity.VideoProcessingStatus;
import com.snapit.interfaceadaptors.entityadaptor.EFrameProcessorInterface;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FrameProcessor")
public class EFrameProcessor implements EFrameProcessorInterface {

    @Id
    private String id;

    private String email;

    private String originalFilename;

    private Integer frameInterval;

    @Enumerated(STRING)
    private VideoProcessingStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime finishedAt;

    private String framesFilename;

    public EFrameProcessor(String id, String email, String originalFilename, Integer frameInterval, VideoProcessingStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.originalFilename = originalFilename;
        this.frameInterval = frameInterval;
        this.status = status;
        this.createdAt = createdAt;
    }
}
