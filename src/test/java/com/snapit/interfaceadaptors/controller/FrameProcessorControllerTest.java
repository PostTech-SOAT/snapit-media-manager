package com.snapit.interfaceadaptors.controller;

import com.snapit.application.usecase.frameprocessor.FindVideoProcessingStatusUseCase;
import com.snapit.application.usecase.frameprocessor.MarkFrameProcessorAsFailedUseCase;
import com.snapit.application.usecase.frameprocessor.MarkFrameProcessorAsFinishedUseCase;
import com.snapit.application.util.exception.ResourceNotFoundException;
import com.snapit.framework.aws.S3Service;
import com.snapit.framework.repository.FrameProcessorService;
import com.snapit.interfaceadaptors.dto.FrameProcessorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class FrameProcessorControllerTest {

    @InjectMocks
    private FrameProcessorController frameProcessorController;

    @Mock
    private S3Service s3Service;

    @Mock
    private FrameProcessorService frameProcessorService;

    private String id;

    private String email;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        this.id = UUID.randomUUID().toString();
        this.email = "test@example.com";
    }

    @Test
    void shouldDownloadAnGetFileNotFound() {
        when(frameProcessorService.findFileNameById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> frameProcessorController.download(email, id, s3Service, frameProcessorService));
        verify(frameProcessorService, times(1)).findFileNameById(id);
        verify(s3Service, never()).getFramesFile(anyString());
    }

    @Test
    void shouldFindProcessingStatusByEmail() {
        List<FrameProcessorDTO> dtoList = Collections.emptyList();

        FindVideoProcessingStatusUseCase useCase = mock(FindVideoProcessingStatusUseCase.class);
        when(useCase.findProcessingStatusByEmail(email)).thenReturn(Collections.emptyList());

        List<FrameProcessorDTO> result = frameProcessorController.findProcessingStatusByEmail(email, frameProcessorService);

        assertNotNull(result);
        assertEquals(dtoList.size(), result.size());
    }

    @Test
    void shouldMarkFrameProcessorAsFinished() {
        String framesFilename = "frames.zip";

        MarkFrameProcessorAsFinishedUseCase useCase = mock(MarkFrameProcessorAsFinishedUseCase.class);
        doNothing().when(useCase).markFrameProcessorAsFinished(id, framesFilename);

        assertDoesNotThrow(() -> frameProcessorController.markFrameProcessorAsFinished(id, framesFilename, frameProcessorService));
    }

    @Test
    void shouldMarkFrameProcessorAsFailed() {
        MarkFrameProcessorAsFailedUseCase useCase = mock(MarkFrameProcessorAsFailedUseCase.class);
        doNothing().when(useCase).markFrameProcessorAsFailed(id);

        assertDoesNotThrow(() -> frameProcessorController.markFrameProcessorAsFailed(id, frameProcessorService));
    }
}
