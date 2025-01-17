package com.snapit.interfaceadaptors.gateway;

import com.snapit.application.interfacegateway.FrameProcessorGateway;
import com.snapit.domain.entity.FrameProcessor;
import com.snapit.domain.entity.VideoProcessingStatus;
import com.snapit.framework.entity.EFrameProcessor;
import com.snapit.interfaceadaptors.repositoryadapter.FrameProcessorRepositoryAdapter;

import java.util.List;

public class FrameProcessorGatewayJPA implements FrameProcessorGateway {

    private final FrameProcessorRepositoryAdapter repository;

    public FrameProcessorGatewayJPA(FrameProcessorRepositoryAdapter repository) {
        this.repository = repository;
    }

    @Override
    public void downloadFrames() {

    }

    @Override
    public List<FrameProcessor> findProcessingStatusByEmail(String email) {
        return repository.findProcessingStatusByEmail(email).stream().map(this::entityToDomain).toList();
    }

    public FrameProcessor entityToDomain(EFrameProcessor eFrameProcessor) {
        return new FrameProcessor(eFrameProcessor.getId(), eFrameProcessor.getEmail(),
                eFrameProcessor.getOriginalFilename(), eFrameProcessor.getFrameInterval(), VideoProcessingStatus.valueOf(eFrameProcessor.getStatus()),
                eFrameProcessor.getCreatedAt(), eFrameProcessor.getFinishedAt(), eFrameProcessor.getFramesFilename());
    }
}
