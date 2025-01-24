package com.snapit.interfaceadaptors.controller;

import com.snapit.application.interfaces.BucketService;
import com.snapit.application.usecase.video.DownloadVideoUseCase;
import com.snapit.application.usecase.video.UploadVideoUseCase;
import com.snapit.domain.entity.FrameProcessor;
import com.snapit.framework.repository.FrameProcessorService;
import com.snapit.interfaceadaptors.gateway.FrameProcessorGatewayJPA;
import org.springframework.core.io.InputStreamResource;

import java.io.InputStream;
import java.time.LocalDateTime;

import static com.snapit.domain.entity.VideoProcessingStatus.PROCESSING;

public class VideoController {

    public void upload(Integer frameInterval, InputStream video, String originalFilename, String email, BucketService bucketService, FrameProcessorService frameService) {
        UploadVideoUseCase useCase = new UploadVideoUseCase(new FrameProcessorGatewayJPA(frameService));
        FrameProcessor frameProcessor = new FrameProcessor(email, originalFilename, frameInterval, PROCESSING, LocalDateTime.now());
        useCase.upload(frameProcessor, video, bucketService);
    }

    public InputStreamResource download(String email, String id, BucketService service, FrameProcessorService frameService) {
        DownloadVideoUseCase useCase = new DownloadVideoUseCase(new FrameProcessorGatewayJPA(frameService));
        return useCase.download(email, id, service);
    }
}
