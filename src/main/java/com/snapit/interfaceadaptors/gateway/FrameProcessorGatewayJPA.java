package com.snapit.interfaceadaptors.gateway;

import com.snapit.application.interfacegateway.FrameProcessorDatabaseGateway;
import com.snapit.domain.entity.FrameProcessor;
import com.snapit.domain.entity.VideoProcessingStatus;
import com.snapit.interfaceadaptors.entityadaptor.EFrameProcessorInterface;
import com.snapit.interfaceadaptors.gateway.repositorydto.FrameProcessorRepositoryDTO;
import com.snapit.interfaceadaptors.repositoryadapter.FrameProcessorServiceAdapter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class FrameProcessorGatewayJPA implements FrameProcessorDatabaseGateway {

    private final FrameProcessorServiceAdapter repository;

    public FrameProcessorGatewayJPA(FrameProcessorServiceAdapter repository) {
        this.repository = repository;
    }

    @Override
    public List<FrameProcessor> findProcessingStatusByEmail(String email) {
        return repository.findProcessingStatusByEmail(email).stream().map(this::entityToDomain).toList();
    }

    @Override
    public void create(FrameProcessor frameProcessor) {
        repository.create(domainToRepositoryDto(frameProcessor));
    }

    @Override
    public void markProcessorAsFinished(String id, VideoProcessingStatus status, String framesFilename,
                                        LocalDateTime finishedAt) {
        repository.markFrameProcessorAsFinished(id, status, framesFilename, finishedAt);
    }

    @Override
    public void markProcessorAsFailed(String id, VideoProcessingStatus status, LocalDateTime finishedAt) {
        repository.markFrameProcessorAsFailed(id, status, finishedAt);
    }

    public Optional<FrameProcessor> findFileNameById(String id) {
        return repository.findFileNameById(id).map(this::entityToDomain);
    }

    @Override
    public Optional<FrameProcessor> findByFilenameAndEmail(String originalFilename, String email) {
        return repository.findByFilenameAndEmail(originalFilename, email).map(this::entityToDomain);
    }

    private FrameProcessor entityToDomain(EFrameProcessorInterface eFrameProcessor) {
        return new FrameProcessor(eFrameProcessor.getId(), eFrameProcessor.getEmail(),
                eFrameProcessor.getOriginalFilename(), eFrameProcessor.getFrameInterval(), eFrameProcessor.getStatus(),
                eFrameProcessor.getCreatedAt(), eFrameProcessor.getFinishedAt(), eFrameProcessor.getFramesFilename());
    }

    private FrameProcessorRepositoryDTO domainToRepositoryDto(FrameProcessor frameProcessor) {
        return new FrameProcessorRepositoryDTO(frameProcessor.getId(), frameProcessor.getEmail(),
                frameProcessor.getOriginalFilename(), frameProcessor.getFrameInterval(), frameProcessor.getStatus(), frameProcessor.getCreatedAt());
    }
}
