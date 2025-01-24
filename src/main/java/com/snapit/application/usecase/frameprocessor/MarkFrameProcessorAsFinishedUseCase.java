package com.snapit.application.usecase.frameprocessor;

import com.snapit.application.interfacegateway.FrameProcessorDatabaseGateway;

import java.time.LocalDateTime;

import static com.snapit.domain.entity.VideoProcessingStatus.FINISHED;

public class MarkFrameProcessorAsFinishedUseCase {

    private final FrameProcessorDatabaseGateway gateway;

    public MarkFrameProcessorAsFinishedUseCase(FrameProcessorDatabaseGateway gateway) {
        this.gateway = gateway;
    }

    public void markFrameProcessorAsFinished(String id, String framesFilename) {
        gateway.markProcessorAsFinished(id, FINISHED, framesFilename, LocalDateTime.now());
    }

}
