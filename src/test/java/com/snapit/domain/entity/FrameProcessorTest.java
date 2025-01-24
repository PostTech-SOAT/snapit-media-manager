package com.snapit.domain.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FrameProcessorTest {

    @Test
    void shouldCreateFrameProcessor() {
        FrameProcessor frameProcessor = new FrameProcessor(UUID.randomUUID().toString(), "test@email.com", "originalFilename", 20,
                VideoProcessingStatus.PROCESSING, LocalDateTime.of(2025, 1, 22, 12, 20), LocalDateTime.now(), "framesFilename");

        assertNotNull(frameProcessor);
    }

    @Test
    void shouldCreateFrameProcessorWithoutId() {
        FrameProcessor frameProcessor = new FrameProcessor("test@email.com", "originalFilename", 20,
                VideoProcessingStatus.PROCESSING, LocalDateTime.of(2025, 1, 22, 12, 20));

        assertNotNull(frameProcessor);
        assertNotNull(frameProcessor.getId());
    }

    @Test
    void shouldThrowExceptionWhenFrameIntervalIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new FrameProcessor("test@email.com", "originalFilename", 0,
                VideoProcessingStatus.PROCESSING, LocalDateTime.of(2025, 1, 22, 12, 20)));
    }

    @Test
    void shouldThrowExceptionWhenEmailIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new FrameProcessor("email", "originalFilename", 20,
                VideoProcessingStatus.PROCESSING, LocalDateTime.of(2025, 1, 22, 12, 20)));
    }
}
