package com.snapit.application.usecase.frameprocessor;

import com.snapit.application.interfacegateway.FrameProcessorDatabaseGateway;
import com.snapit.application.util.exception.ResourceNotFoundException;
import com.snapit.framework.aws.S3Service;
import org.springframework.core.io.InputStreamResource;

import static com.snapit.application.util.BucketUtils.getBucketKey;

public class DownloadFramesUseCase {

    private final FrameProcessorDatabaseGateway databaseGateway;

    public DownloadFramesUseCase(FrameProcessorDatabaseGateway databaseGateway) {
        this.databaseGateway = databaseGateway;
    }

    public InputStreamResource downloadFrames(String email, String id, S3Service bucketService) {
        String filename = databaseGateway.findFileNameById(id).orElseThrow(() -> new ResourceNotFoundException("File not found")).getFramesFilename();
        return new InputStreamResource(bucketService.getFramesFile(getBucketKey(email, filename)), filename);
    }

}
