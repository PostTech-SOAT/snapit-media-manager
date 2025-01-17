package com.snapit.interfaceadaptors.controller;

import com.snapit.application.usecase.frameprocessor.DownloadFramesUseCase;
import com.snapit.application.usecase.frameprocessor.FindVideoProcessingStatusUseCase;
import com.snapit.interfaceadaptors.gateway.FrameProcessorGatewayJPA;
import com.snapit.interfaceadaptors.repositoryadapter.FrameProcessorRepositoryAdapter;

public class FrameProcessorController {

    public void download(FrameProcessorRepositoryAdapter repository) {
        DownloadFramesUseCase useCase = new DownloadFramesUseCase(new FrameProcessorGatewayJPA(repository));
        useCase.downloadFrames();
    }

    public void findProcessingStatusByEmail(String email, FrameProcessorRepositoryAdapter repository) {
        FindVideoProcessingStatusUseCase useCase = new FindVideoProcessingStatusUseCase(new FrameProcessorGatewayJPA(repository));
        useCase.findProcessingStatusByEmail(email);
    }

}
