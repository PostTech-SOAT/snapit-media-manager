package com.snapit.interfaceadaptors.controller;

import com.snapit.application.usecase.frameprocessor.DownloadFramesUseCase;
import com.snapit.application.usecase.frameprocessor.FindVideoProcessingStatusUseCase;
import com.snapit.framework.aws.S3Service;
import com.snapit.interfaceadaptors.dto.FrameProcessorDTO;
import com.snapit.interfaceadaptors.gateway.FrameProcessorGatewayJPA;
import com.snapit.interfaceadaptors.presenter.FrameProcessorPresenter;
import com.snapit.interfaceadaptors.repositoryadapter.FrameProcessorServiceAdapter;

import java.util.List;

public class FrameProcessorController {

    public void download(S3Service bucketService, FrameProcessorServiceAdapter service) {
        DownloadFramesUseCase useCase = new DownloadFramesUseCase(new FrameProcessorGatewayJPA(service));
        useCase.downloadFrames();
    }

    public List<FrameProcessorDTO> findProcessingStatusByEmail(String email, FrameProcessorServiceAdapter service) {
        FindVideoProcessingStatusUseCase useCase = new FindVideoProcessingStatusUseCase(new FrameProcessorGatewayJPA(service));
        return useCase.findProcessingStatusByEmail(email).stream().map(FrameProcessorPresenter::toDTO).toList();
    }

}
