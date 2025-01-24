package com.snapit.framework.repository;

import com.snapit.framework.entity.EFrameProcessor;
import com.snapit.interfaceadaptors.gateway.repositorydto.FrameProcessorRepositoryDTO;
import com.snapit.interfaceadaptors.repositoryadapter.FrameProcessorServiceAdapter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class FrameProcessorService implements FrameProcessorServiceAdapter {

    private final FrameProcessorRepository repository;

    @Override
    public void downloadFrames(String videoPath) {

    }

    @Override
    public List<? extends EFrameProcessor> findProcessingStatusByEmail(String email) {
        return repository.findAllByEmail(email);
    }

    @Override
    public void create(FrameProcessorRepositoryDTO dto) {
        repository.save(new EFrameProcessor(dto.getId(), dto.getEmail(), dto.getOriginalFilename(), dto.getFrameInterval(), dto.getStatus(), dto.getCreatedAt()));
    }

}
