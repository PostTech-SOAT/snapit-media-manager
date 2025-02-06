package com.snapit.framework.api;

import com.snapit.application.util.exception.ConflictException;
import com.snapit.framework.aws.S3Service;
import com.snapit.framework.repository.FrameProcessorService;
import com.snapit.interfaceadaptors.controller.VideoController;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.snapit.application.util.FileUtils.getVideoName;
import static com.snapit.framework.context.ContextHolder.getEmail;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/videos")
public class VideoAPI {

    private final S3Service bucketService;
    private final FrameProcessorService frameService;

    @PostMapping("/upload")
    public ResponseEntity<Object> upload(@RequestParam("frameInterval") Integer frameInterval, @RequestParam("videoFile") MultipartFile video) {
        VideoController controller = new VideoController();
        try {
            controller.upload(frameInterval, video.getInputStream(), video.getOriginalFilename(),
                    getEmail(), bucketService, frameService);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
        } catch (ConflictException e) {
            return ResponseEntity.status(CONFLICT).build();
        }
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<InputStreamResource> download(@PathVariable String id) {
        VideoController controller = new VideoController();
        try {
            InputStreamResource video = controller.download(getEmail(), id, bucketService, frameService);
            return ResponseEntity.ok()
                    .header(CONTENT_DISPOSITION, "attachment;filename=" + getVideoName(video.getDescription()))
                    .body(video);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
