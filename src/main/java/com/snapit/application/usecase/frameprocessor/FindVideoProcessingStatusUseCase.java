package com.snapit.application.usecase.frameprocessor;

import com.snapit.application.interfacegateway.FrameProcessorDatabaseGateway;
import com.snapit.domain.entity.FrameProcessor;

import java.util.List;

public class FindVideoProcessingStatusUseCase {

    private final FrameProcessorDatabaseGateway gateway;

    public FindVideoProcessingStatusUseCase(FrameProcessorDatabaseGateway gateway) {
        this.gateway = gateway;
    }

    public List<FrameProcessor> findProcessingStatusByEmail(String email) {
        return gateway.findProcessingStatusByEmail(email);
    }

}
