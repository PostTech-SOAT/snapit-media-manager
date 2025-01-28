package com.snapit.application.usecase.frameprocessor;

import com.snapit.application.interfacegateway.FrameProcessorDatabaseGateway;
import com.snapit.domain.entity.FrameProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.snapit.domain.entity.VideoProcessingStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FindVideoProcessingStatusUseCaseTest {

    private FindVideoProcessingStatusUseCase useCase;

    @Mock
    private FrameProcessorDatabaseGateway databaseGateway;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new FindVideoProcessingStatusUseCase(databaseGateway);
    }

    @Test
    void shouldFindProcessingStatusByEmail() {

        FrameProcessor frameProcessor = new FrameProcessor(UUID.randomUUID().toString(), "email@test.com", "firstVideo.mp4", 10, FINISHED,
                LocalDateTime.of(2025, 1, 22, 12, 20), LocalDateTime.of(2025, 1, 22, 16, 20), "firstVideo.zip");
        FrameProcessor frameProcessor2 = new FrameProcessor(UUID.randomUUID().toString(), "email@test.com", "secondVideo.mp4", 10, FINISHED,
                LocalDateTime.of(2025, 1, 22, 13, 20), LocalDateTime.of(2025, 1, 22, 13, 20), "secondVideo.zip");
        FrameProcessor frameProcessor3 = new FrameProcessor(UUID.randomUUID().toString(), "email@test.com", "thirdVideo.mp4", 10, ERROR,
                LocalDateTime.of(2025, 1, 23, 12, 20), LocalDateTime.of(2025, 1, 23, 16, 20), "thirdVideo.zip");
        FrameProcessor frameProcessor4 = new FrameProcessor(UUID.randomUUID().toString(), "email@test.com", "thirdVideo.mp4", 10, PROCESSING,
                LocalDateTime.of(2025, 1, 23, 12, 20), null, null);

        List<FrameProcessor> frames = List.of(frameProcessor, frameProcessor2, frameProcessor3, frameProcessor4);

        when(databaseGateway.findProcessingStatusByEmail(any(String.class))).thenReturn(frames);

        List<FrameProcessor> framesProcessingStatusByEmail = useCase.findProcessingStatusByEmail("email@test.com");

        assertNotNull(framesProcessingStatusByEmail);
        assertEquals(4, framesProcessingStatusByEmail.size());

    }

}
