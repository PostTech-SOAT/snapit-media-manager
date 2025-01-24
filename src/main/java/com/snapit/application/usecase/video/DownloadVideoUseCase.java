package com.snapit.application.usecase.video;

import com.snapit.application.interfacegateway.FrameProcessorDatabaseGateway;
import com.snapit.application.interfaces.BucketService;
import com.snapit.application.util.exception.ResourceNotFoundException;
import org.springframework.core.io.InputStreamResource;

import static com.snapit.application.util.BucketUtils.getBucketKey;

public class DownloadVideoUseCase {

    private final FrameProcessorDatabaseGateway databaseGateway;

    public DownloadVideoUseCase(FrameProcessorDatabaseGateway databaseGateway) {
        this.databaseGateway = databaseGateway;
    }

    public InputStreamResource download(String email, String id, BucketService service) {
        String filename = databaseGateway.findFileNameById(id).orElseThrow(() -> new ResourceNotFoundException("File not found")).getOriginalFilename();
        return new InputStreamResource(service.getOriginalFile(getBucketKey(email, filename)), filename);
    }

}
