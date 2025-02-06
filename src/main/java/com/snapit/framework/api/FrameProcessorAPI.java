package com.snapit.framework.api;

import com.snapit.application.util.exception.ResourceNotFoundException;
import com.snapit.framework.aws.S3Service;
import com.snapit.framework.repository.FrameProcessorService;
import com.snapit.interfaceadaptors.controller.FrameProcessorController;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.snapit.application.util.FileUtils.getVideoName;
import static com.snapit.framework.context.ContextHolder.getEmail;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/frames")
public class FrameProcessorAPI {

    private final S3Service bucketService;
    private final FrameProcessorService service;

    @GetMapping("/{id}/download")
    public ResponseEntity<InputStreamResource> download(@PathVariable String id) {
        FrameProcessorController controller = new FrameProcessorController();
        try {
            InputStreamResource video = controller.download(getEmail(), id, bucketService, service);
            return ResponseEntity.ok()
                    .header(CONTENT_DISPOSITION, "attachment;filename=" + getVideoName(video.getDescription()))
                    .body(video);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/processor")
    public ResponseEntity<Object> findProcessingStatusByEmail() {
        FrameProcessorController controller = new FrameProcessorController();
        return ResponseEntity.ok(controller.findProcessingStatusByEmail(getEmail(), service));
    }

}
