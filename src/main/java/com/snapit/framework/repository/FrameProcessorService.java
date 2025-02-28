package com.snapit.framework.repository;

import com.snapit.domain.entity.VideoProcessingStatus;
import com.snapit.framework.entity.EFrameProcessor;
import com.snapit.interfaceadaptors.entityadaptor.EFrameProcessorInterface;
import com.snapit.interfaceadaptors.gateway.repositorydto.FrameProcessorRepositoryDTO;
import com.snapit.interfaceadaptors.repositoryadapter.FrameProcessorServiceAdapter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
@AllArgsConstructor
public class FrameProcessorService implements FrameProcessorServiceAdapter {

    private final FrameProcessorRepository repository;

    @Override
    public List<? extends EFrameProcessorInterface> findProcessingStatusByEmail(String email) {
        return repository.findAllByEmail(email);
    }

    @Override
    public void create(FrameProcessorRepositoryDTO dto) {
        repository.save(new EFrameProcessor(dto.getId(), dto.getEmail(), dto.getOriginalFilename(), dto.getFrameInterval(), dto.getStatus(), dto.getCreatedAt()));
    }

    @Override
    public void markFrameProcessorAsFinished(String id, VideoProcessingStatus status, String framesFilename,
                                             LocalDateTime finishedAt) {
        repository.markFrameProcessorAsFinished(id, status.toString(), framesFilename, finishedAt);
    }

    @Override
    public void markFrameProcessorAsFailed(String id, VideoProcessingStatus status,
                                             LocalDateTime finishedAt) {
        repository.markFrameProcessorAsFailed(id, status.toString(), finishedAt);
    }

    public Optional<? extends EFrameProcessorInterface> findFileNameById(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<? extends EFrameProcessorInterface> findByFilenameAndEmail(String originalFilename, String email) {
        return repository.findByOriginalFilenameAndEmail(originalFilename, email);
    }

}
