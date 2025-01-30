package com.snapit.application.usecase.frameprocessor;

import com.snapit.application.interfacegateway.FrameProcessorDatabaseGateway;
import com.snapit.domain.entity.VideoProcessingStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MarkFrameProcessorAsFinishedUseCaseTest {

    private MarkFrameProcessorAsFinishedUseCase useCase;

    @Mock
    private FrameProcessorDatabaseGateway databaseGateway;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new MarkFrameProcessorAsFinishedUseCase(databaseGateway);
    }

    @Test
    void shouldMarkFrameProcessorAsFinished() {

        doNothing().when(databaseGateway).markProcessorAsFinished(any(String.class), any(VideoProcessingStatus.class), any(String.class), any(LocalDateTime.class));

        useCase.markFrameProcessorAsFinished(UUID.randomUUID().toString(), "firstVideo.zip");

        verify(databaseGateway, times(1))
                .markProcessorAsFinished(any(String.class), any(VideoProcessingStatus.class), any(String.class), any(LocalDateTime.class));
    }
}
