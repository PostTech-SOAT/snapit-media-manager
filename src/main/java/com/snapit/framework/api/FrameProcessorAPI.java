package com.snapit.framework.api;

import com.snapit.framework.repository.FrameProcessorRepositoryImpl;
import com.snapit.interfaceadaptors.controller.FrameProcessorController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/frames")
public class FrameProcessorAPI {

    private final FrameProcessorRepositoryImpl repository;

    @PostMapping("/download")
    public void download() {
        FrameProcessorController controller = new FrameProcessorController();
        controller.download(repository);
    }

    @GetMapping("/processor")
    public void findProcessingStatusByEmail(@RequestHeader("userEmail") String email) {
        FrameProcessorController controller = new FrameProcessorController();
        controller.findProcessingStatusByEmail(email, repository);
    }

}
