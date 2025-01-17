package com.snapit.application.interfacegateway;

import com.snapit.domain.entity.FrameProcessor;

import java.util.List;

public interface FrameProcessorGateway {

    void downloadFrames();
    List<FrameProcessor> findProcessingStatusByEmail(String email);

}
