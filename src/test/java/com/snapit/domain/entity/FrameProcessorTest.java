package com.snapit.domain.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.snapit.domain.entity.VideoProcessingStatus.FINISHED;
import static com.snapit.util.FrameProcessorUtils.getFrameProcessor;
import static org.junit.jupiter.api.Assertions.*;

class FrameProcessorTest {

    @Test
    void shouldCreateFrameProcessor() {
        FrameProcessor frameProcessor = getFrameProcessor();

        assertNotNull(frameProcessor);
    }

    @Test
    void shouldCreateFrameProcessorWithoutId() {
        FrameProcessor frameProcessor = getFrameProcessor();

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

    @Test
    void shouldGetFinishedAt() {
        FrameProcessor frameProcessor = getFrameProcessor();

        assertNotNull(frameProcessor.getFinishedAt());
        assertEquals(LocalDateTime.of(2025, 1, 22, 16, 20), frameProcessor.getFinishedAt());
    }

    @Test
    void shouldGetCreatedAt() {
        FrameProcessor frameProcessor = getFrameProcessor();

        assertNotNull(frameProcessor.getCreatedAt());
        assertEquals(LocalDateTime.of(2025, 1, 22, 12, 20), frameProcessor.getCreatedAt());
    }

    @Test
    void shouldGetStatus() {
        FrameProcessor frameProcessor = getFrameProcessor();

        assertNotNull(frameProcessor.getStatus());
        assertEquals(FINISHED, frameProcessor.getStatus());
    }
}
