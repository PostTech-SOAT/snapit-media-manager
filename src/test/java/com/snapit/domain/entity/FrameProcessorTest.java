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
                VideoProcessingStatus.PROCESSING, LocalDateTime.of(2025, 1, 22, 12, 20), null, null);

        assertNotNull(frameProcessor);
        assertNotNull(frameProcessor.getId());
    }

    @Test
    void shouldThrowExceptionWhenFrameIntervalIsLessThanTen() {
        assertThrows(IllegalArgumentException.class, () -> new FrameProcessor("test@email.com", "originalFilename", 0,
                VideoProcessingStatus.PROCESSING, LocalDateTime.of(2025, 1, 22, 12, 20), null, null));
    }

    @Test
    void shouldThrowExceptionWhenFrameIntervalIsGreaterThanSixty() {
        assertThrows(IllegalArgumentException.class, () -> new FrameProcessor("test@email.com", "originalFilename", 61,
                VideoProcessingStatus.PROCESSING, LocalDateTime.of(2025, 1, 22, 12, 20), null, null));
    }

    @Test
    void shouldThrowExceptionWhenFrameIntervalIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new FrameProcessor("test@email.com", "originalFilename", null,
                VideoProcessingStatus.PROCESSING, LocalDateTime.of(2025, 1, 22, 12, 20), null, null));
    }

    @Test
    void shouldThrowExceptionWhenEmailIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new FrameProcessor("email", "originalFilename", 20,
                VideoProcessingStatus.PROCESSING, LocalDateTime.of(2025, 1, 22, 12, 20), null, null));
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new FrameProcessor(null, "originalFilename", 20,
                VideoProcessingStatus.PROCESSING, LocalDateTime.of(2025, 1, 22, 12, 20), null, null));
    }

}
