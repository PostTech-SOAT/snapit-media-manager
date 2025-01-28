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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

class MarkFrameProcessorAsFailedUseCaseTest {

    private MarkFrameProcessorAsFailedUseCase useCase;

    @Mock
    private FrameProcessorDatabaseGateway databaseGateway;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new MarkFrameProcessorAsFailedUseCase(databaseGateway);
    }

    @Test
    void shouldMarkFrameProcessorAsFailed() {

        doNothing().when(databaseGateway).markProcessorAsFailed(any(String.class), any(VideoProcessingStatus.class), any(LocalDateTime.class));

        useCase.markFrameProcessorAsFailed(UUID.randomUUID().toString());

        verify(databaseGateway).markProcessorAsFailed(any(String.class), any(VideoProcessingStatus.class), any(LocalDateTime.class));

    }
}
