package com.snapit.interfaceadaptors.entityadaptor;

import com.snapit.domain.entity.VideoProcessingStatus;

import java.time.LocalDateTime;

public interface EFrameProcessorInterface {

    String getId();

    String getEmail();

    String getOriginalFilename();

    Integer getFrameInterval();

    VideoProcessingStatus getStatus();

    LocalDateTime getCreatedAt();

    LocalDateTime getFinishedAt();

    String getFramesFilename();

}
