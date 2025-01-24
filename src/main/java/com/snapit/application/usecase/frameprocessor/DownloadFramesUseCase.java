package com.snapit.application.usecase.frameprocessor;

import com.snapit.application.interfacegateway.FrameProcessorDatabaseGateway;

public class DownloadFramesUseCase {

    private final FrameProcessorDatabaseGateway gateway;

    public DownloadFramesUseCase(FrameProcessorDatabaseGateway gateway) {
        this.gateway = gateway;
    }

    public void downloadFrames() {
        gateway.downloadFrames();
    }

}
