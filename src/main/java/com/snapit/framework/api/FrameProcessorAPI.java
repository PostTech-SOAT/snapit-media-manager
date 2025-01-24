package com.snapit.framework.api;

import com.snapit.application.util.exception.ResourceNotFoundException;
import com.snapit.framework.aws.S3Service;
import com.snapit.framework.repository.FrameProcessorService;
import com.snapit.interfaceadaptors.controller.FrameProcessorController;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.snapit.application.util.FileUtils.getVideoName;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/frames")
public class FrameProcessorAPI {

    private final S3Service bucketService;
    private final FrameProcessorService service;

    @GetMapping("/{id}/download")
    public ResponseEntity<InputStreamResource> download(@RequestHeader("userEmail") String email, @PathVariable String id) {
        FrameProcessorController controller = new FrameProcessorController();
        try {
            InputStreamResource video = controller.download(email, id, bucketService, service);
            return ResponseEntity.ok()
                    .header(CONTENT_DISPOSITION, "attachment;filename=" + getVideoName(video.getDescription()))
                    .body(video);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/processor")
    public ResponseEntity<Object> findProcessingStatusByEmail(@RequestHeader("userEmail") String email) {
        FrameProcessorController controller = new FrameProcessorController();
        return ResponseEntity.ok(controller.findProcessingStatusByEmail(email, service));
    }

}
