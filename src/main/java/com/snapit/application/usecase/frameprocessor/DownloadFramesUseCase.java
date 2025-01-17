package com.snapit.application.usecase.frameprocessor;

import com.snapit.application.interfacegateway.FrameProcessorGateway;

public class DownloadFramesUseCase {

    private final FrameProcessorGateway gateway;

    public DownloadFramesUseCase(FrameProcessorGateway gateway) {
        this.gateway = gateway;
    }

    public void downloadFrames() {
        gateway.downloadFrames();
    }

}
