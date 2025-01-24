package com.snapit.framework.api;

import com.snapit.framework.aws.S3Service;
import com.snapit.framework.repository.FrameProcessorService;
import com.snapit.interfaceadaptors.controller.VideoController;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/videos")
public class VideoAPI {

    private final S3Service bucketService;
    private final FrameProcessorService frameService;

    @PostMapping("/upload")
    public ResponseEntity<Object> upload(@RequestParam("frameInterval") Integer frameInterval, @RequestParam("videoFile") MultipartFile video, @RequestHeader("userEmail") String email) {
        VideoController controller = new VideoController();
        try {
            controller.upload(frameInterval, video.getInputStream(), video.getOriginalFilename(),
                    email, bucketService, frameService);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
        }
    }

    @GetMapping("/{filename}/download")
    public ResponseEntity<InputStreamResource> download(@RequestHeader("userEmail") String email, @PathVariable String filename) {
        VideoController controller = new VideoController();
        return ResponseEntity.ok()
                .header(CONTENT_DISPOSITION, "attachment;filename=" + filename)
                .body(controller.download(bucketService, email, filename));
    }
}
