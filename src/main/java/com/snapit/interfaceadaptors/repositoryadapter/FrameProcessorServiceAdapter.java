package com.snapit.interfaceadaptors.repositoryadapter;

import com.snapit.domain.entity.VideoProcessingStatus;
import com.snapit.framework.entity.EFrameProcessor;
import com.snapit.interfaceadaptors.gateway.repositorydto.FrameProcessorRepositoryDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface FrameProcessorServiceAdapter {

    void downloadFrames(String videoPath);
    List<? extends EFrameProcessor> findProcessingStatusByEmail(String email);
    void create(FrameProcessorRepositoryDTO frameProcessorRepositoryDTO);
    void markFrameProcessorAsFinished(String id, VideoProcessingStatus status, String framesFilename,
                                      LocalDateTime finishedAt);
    void markFrameProcessorAsFailed(String id, VideoProcessingStatus status, LocalDateTime finishedAt);
}
