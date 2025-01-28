package com.snapit.util;

import com.snapit.domain.entity.FrameProcessor;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.snapit.domain.entity.VideoProcessingStatus.FINISHED;

public class FrameProcessorUtils {

    public static FrameProcessor getFrameProcessor() {
        return new FrameProcessor(UUID.randomUUID().toString(), "email@test.com", "firstVideo.mp4", 10, FINISHED,
                LocalDateTime.of(2025, 1, 22, 12, 20),
                LocalDateTime.of(2025, 1, 22, 16, 20), "firstVideo.zip");
    }
}
