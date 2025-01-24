package com.snapit.interfaceadaptors.repositoryadapter;

import com.snapit.framework.entity.EFrameProcessor;
import com.snapit.interfaceadaptors.gateway.repositorydto.FrameProcessorRepositoryDTO;

import java.util.List;

public interface FrameProcessorServiceAdapter {

    void downloadFrames(String videoPath);
    List<? extends EFrameProcessor> findProcessingStatusByEmail(String email);
    void create(FrameProcessorRepositoryDTO frameProcessorRepositoryDTO);

}
