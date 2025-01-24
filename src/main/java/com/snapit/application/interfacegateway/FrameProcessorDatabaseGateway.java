package com.snapit.application.interfacegateway;

import com.snapit.domain.entity.FrameProcessor;
import com.snapit.domain.entity.VideoProcessingStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FrameProcessorDatabaseGateway {

    List<FrameProcessor> findProcessingStatusByEmail(String email);

    void create(FrameProcessor frameProcessor);

    void markProcessorAsFinished(String id, VideoProcessingStatus status, String framesFilename, LocalDateTime finishedAt);

    void markProcessorAsFailed(String id, VideoProcessingStatus status, LocalDateTime finishedAt);

    Optional<FrameProcessor> findFileNameById(String id);

    Optional<FrameProcessor> findByFilenameAndEmail(String originalFilename, String email);
}
