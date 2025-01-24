package com.snapit.application.usecase.frameprocessor;

import com.snapit.application.interfacegateway.FrameProcessorDatabaseGateway;

import java.time.LocalDateTime;

import static com.snapit.domain.entity.VideoProcessingStatus.ERROR;

public class MarkFrameProcessorAsFailedUseCase {

    private final FrameProcessorDatabaseGateway gateway;

    public MarkFrameProcessorAsFailedUseCase(FrameProcessorDatabaseGateway gateway) {
        this.gateway = gateway;
    }

    public void markFrameProcessorAsFailed(String id) {
        gateway.markProcessorAsFailed(id, ERROR, LocalDateTime.now());
    }

}
