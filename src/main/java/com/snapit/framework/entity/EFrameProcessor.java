package com.snapit.framework.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FrameProcessor")
public class EFrameProcessor {

    @Id
    private String id;

    private String email;

    private String originalFilename;

    private String frameInterval;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime finishedAt;

    private String framesFilename;

}
