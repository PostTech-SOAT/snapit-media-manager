package com.snapit.framework.api;

import com.snapit.interfaceadaptors.controller.VideoController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/videos")
public class VideoAPI {

    @PostMapping("/videos/upload")
    public void uploadVideo(@RequestParam("videoFile") MultipartFile video) {
        VideoController controller = new VideoController();

        controller.upload();
    }

    @GetMapping("/videos/download")
    public void downloadVideo() {
        VideoController controller = new VideoController();
        controller.download();
    }
}
