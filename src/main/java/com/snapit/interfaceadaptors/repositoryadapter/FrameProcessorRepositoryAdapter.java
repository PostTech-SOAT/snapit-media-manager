package com.snapit.interfaceadaptors.repositoryadapter;

import com.snapit.framework.entity.EFrameProcessor;

import java.util.List;

public interface FrameProcessorRepositoryAdapter {

    void uploadVideo(String videoPath);
    void downloadFrames(String videoPath);
    List<EFrameProcessor> findProcessingStatusByEmail(String email);

}
