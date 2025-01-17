package com.snapit.application.usecase.frameprocessor;

import com.snapit.application.interfacegateway.FrameProcessorGateway;

public class FindVideoProcessingStatusUseCase {

    private final FrameProcessorGateway gateway;

    public FindVideoProcessingStatusUseCase(FrameProcessorGateway gateway) {
        this.gateway = gateway;
    }

    public void findProcessingStatusByEmail(String email) {
        gateway.findProcessingStatusByEmail(email);
    }

}
