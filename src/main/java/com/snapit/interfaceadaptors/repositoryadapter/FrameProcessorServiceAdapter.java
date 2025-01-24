package com.snapit.interfaceadaptors.repositoryadapter;

import com.snapit.domain.entity.VideoProcessingStatus;
import com.snapit.interfaceadaptors.entityadaptor.EFrameProcessorInterface;
import com.snapit.interfaceadaptors.gateway.repositorydto.FrameProcessorRepositoryDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FrameProcessorServiceAdapter {

    List<? extends EFrameProcessorInterface> findProcessingStatusByEmail(String email);
    void create(FrameProcessorRepositoryDTO frameProcessorRepositoryDTO);
    void markFrameProcessorAsFinished(String id, VideoProcessingStatus status, String framesFilename,
                                      LocalDateTime finishedAt);
    void markFrameProcessorAsFailed(String id, VideoProcessingStatus status, LocalDateTime finishedAt);
    Optional<? extends EFrameProcessorInterface> findFileNameById(String id);

    Optional<? extends EFrameProcessorInterface> findByFilenameAndEmail(String originalFilename, String email);
}
