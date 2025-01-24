package com.snapit.interfaceadaptors.gateway;

import com.snapit.application.interfacegateway.FrameProcessorDatabaseGateway;
import com.snapit.domain.entity.FrameProcessor;
import com.snapit.domain.entity.VideoProcessingStatus;
import com.snapit.interfaceadaptors.entityadaptor.EFrameProcessorInterface;
import com.snapit.interfaceadaptors.gateway.repositorydto.FrameProcessorRepositoryDTO;
import com.snapit.interfaceadaptors.repositoryadapter.FrameProcessorServiceAdapter;

import java.util.List;

public class FrameProcessorGatewayJPA implements FrameProcessorDatabaseGateway {

    private final FrameProcessorServiceAdapter repository;

    public FrameProcessorGatewayJPA(FrameProcessorServiceAdapter repository) {
        this.repository = repository;
    }

    @Override
    public void downloadFrames() {
        repository.downloadFrames("");
    }

    @Override
    public List<FrameProcessor> findProcessingStatusByEmail(String email) {
        return repository.findProcessingStatusByEmail(email).stream().map(this::entityToDomain).toList();
    }

    @Override
    public void create(FrameProcessor frameProcessor) {
        repository.create(domainToRepositoryDto(frameProcessor));
    }

    public FrameProcessor entityToDomain(EFrameProcessorInterface eFrameProcessor) {
        return new FrameProcessor(eFrameProcessor.getId(), eFrameProcessor.getEmail(),
                eFrameProcessor.getOriginalFilename(), eFrameProcessor.getFrameInterval(), eFrameProcessor.getStatus(),
                eFrameProcessor.getCreatedAt(), eFrameProcessor.getFinishedAt(), eFrameProcessor.getFramesFilename());
    }

    private FrameProcessorRepositoryDTO domainToRepositoryDto(FrameProcessor frameProcessor) {
        return new FrameProcessorRepositoryDTO(frameProcessor.getId(), frameProcessor.getEmail(),
                frameProcessor.getOriginalFilename(), frameProcessor.getFrameInterval(), frameProcessor.getStatus(), frameProcessor.getCreatedAt());
    }
}
