package com.snapit.framework.api;

import com.snapit.framework.aws.S3Service;
import com.snapit.framework.repository.FrameProcessorService;
import com.snapit.interfaceadaptors.controller.FrameProcessorController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/frames")
public class FrameProcessorAPI {

    private final S3Service bucketService;
    private final FrameProcessorService service;

    @PostMapping("/download")
    public void download() {
        FrameProcessorController controller = new FrameProcessorController();
        controller.download(bucketService, service);
    }

    @GetMapping("/processor")
    public ResponseEntity<Object> findProcessingStatusByEmail(@RequestHeader("userEmail") String email) {
        FrameProcessorController controller = new FrameProcessorController();
        return ResponseEntity.ok(controller.findProcessingStatusByEmail(email, service));
    }

}
