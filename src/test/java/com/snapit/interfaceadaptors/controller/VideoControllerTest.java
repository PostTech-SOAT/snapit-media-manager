package com.snapit.interfaceadaptors.controller;

import com.snapit.application.interfacegateway.FrameProcessorDatabaseGateway;
import com.snapit.application.usecase.frameprocessor.DownloadFramesUseCase;
import com.snapit.application.usecase.video.UploadVideoUseCase;
import com.snapit.application.util.exception.ResourceNotFoundException;
import com.snapit.domain.entity.FrameProcessor;
import com.snapit.domain.entity.VideoProcessingStatus;
import com.snapit.framework.aws.S3Service;
import com.snapit.framework.repository.FrameProcessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.InputStreamResource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class VideoControllerTest {

    @InjectMocks
    private VideoController controller;

    @Mock
    private S3Service s3Service;

    @Mock
    private FrameProcessorService frameService;

    @Mock
    private FrameProcessorDatabaseGateway databaseGateway;

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
    void shouldUpload() {
        Integer frameInterval = 30;
        InputStream video = mock(InputStream.class);
        String originalFilename = "video.mp4";

        UploadVideoUseCase useCase = mock(UploadVideoUseCase.class);
        doNothing().when(useCase).upload(any(), eq(video), eq(s3Service));

        assertDoesNotThrow(() -> controller.upload(frameInterval, video, originalFilename, email, s3Service, frameService));
    }

    @Test
    void shouldDownloadSuccess() {
        String filename = "frames.zip";

        FrameProcessor frameProcessor = new FrameProcessor(
                id, email, "original.mp4", 30, VideoProcessingStatus.PROCESSING,
                LocalDateTime.now(), null, filename
        );

        when(databaseGateway.findFileNameById(id)).thenReturn(Optional.of(frameProcessor));

        InputStream mockInputStream = new ByteArrayInputStream("mock data".getBytes());
        when(s3Service.getFramesFile(anyString())).thenReturn(mockInputStream);

        DownloadFramesUseCase useCase = new DownloadFramesUseCase(databaseGateway);

        InputStreamResource result = useCase.downloadFrames(email, id, s3Service);

        assertNotNull(result);
    }

    @Test
    void shouldDownloadAndGetFileNotFound() {
        when(databaseGateway.findFileNameById(id)).thenReturn(Optional.empty());

        DownloadFramesUseCase useCase = new DownloadFramesUseCase(databaseGateway);

        assertThrows(ResourceNotFoundException.class, () -> useCase.downloadFrames(email, id, s3Service));
    }
}
