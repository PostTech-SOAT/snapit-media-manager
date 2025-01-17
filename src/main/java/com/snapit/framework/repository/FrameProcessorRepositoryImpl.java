package com.snapit.framework.repository;

import com.snapit.framework.entity.EFrameProcessor;
import com.snapit.interfaceadaptors.repositoryadapter.FrameProcessorRepositoryAdapter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class FrameProcessorRepositoryImpl implements FrameProcessorRepositoryAdapter {

    private final FrameProcessorRepository repository;

    @Override
    public void uploadVideo(String videoPath) {

    }

    @Override
    public void downloadFrames(String videoPath) {

    }

    @Override
    public List<EFrameProcessor> findProcessingStatusByEmail(String email) {
        return repository.findAllByEmail(email);
    }
}
