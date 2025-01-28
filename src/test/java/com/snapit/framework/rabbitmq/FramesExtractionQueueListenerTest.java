package com.snapit.framework.rabbitmq;

import com.snapit.domain.entity.VideoProcessingStatus;
import com.snapit.framework.repository.FrameProcessorService;
import com.snapit.interfaceadaptors.event.FramesExtractedEvent;
import com.snapit.interfaceadaptors.event.FramesExtractionFailedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class FramesExtractionQueueListenerTest {

    private FramesExtractionQueueListener listener;

    @Mock
    private FrameProcessorService service;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        listener = new FramesExtractionQueueListener(service);
    }

    @Test
    void shouldReceiveSuccessMessage() {
        FramesExtractedEvent event = new FramesExtractedEvent(UUID.randomUUID().toString(), "filename");

        listener.receiveMessage(event);

        verify(service, times(1)).markFrameProcessorAsFinished(any(String.class),
                any(VideoProcessingStatus.class), any(String.class), any(LocalDateTime.class));
    }

    @Test
    void shouldReceiveFailedMessage() {
        FramesExtractionFailedEvent event = new FramesExtractionFailedEvent(UUID.randomUUID().toString());

        listener.receiveMessage(event);

        verify(service, times(1)).markFrameProcessorAsFailed(any(String.class),
                any(VideoProcessingStatus.class), any(LocalDateTime.class));
    }

}
