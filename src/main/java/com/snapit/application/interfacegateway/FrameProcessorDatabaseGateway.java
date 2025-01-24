package com.snapit.application.interfacegateway;

import com.snapit.domain.entity.FrameProcessor;

import java.util.List;

public interface FrameProcessorDatabaseGateway {

    void downloadFrames();
    List<FrameProcessor> findProcessingStatusByEmail(String email);
    void create(FrameProcessor frameProcessor);

}
