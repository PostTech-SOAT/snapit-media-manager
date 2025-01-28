package com.snapit.application.usecase.video;

import com.snapit.application.interfacegateway.FrameProcessorDatabaseGateway;
import com.snapit.application.util.exception.ConflictException;
import com.snapit.application.util.exception.SaveVideoException;
import com.snapit.domain.entity.FrameProcessor;
import com.snapit.framework.aws.S3Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Optional;

import static com.snapit.util.FrameProcessorUtils.getFrameProcessor;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UploadVideoUseCaseTest {

    private UploadVideoUseCase useCase;

    @Mock
    private FrameProcessorDatabaseGateway databaseGateway;

    @Mock
    private S3Service s3Service;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new UploadVideoUseCase(databaseGateway);
    }

    @Test
    void shouldUpload() {

        FrameProcessor frameProcessor = getFrameProcessor();
        ByteArrayInputStream videoStream = new ByteArrayInputStream(new byte[0]);

        when(databaseGateway.findByFilenameAndEmail(any(String.class), any(String.class))).thenReturn(Optional.empty());
        doNothing().when(s3Service).sendToHistoryBucket(any(String.class), any(String.class));
        doNothing().when(s3Service).sendToProcessBucket(any(String.class), any(String.class), any(String.class), any(String.class), any(Integer.class));
        doNothing().when(databaseGateway).create(any(FrameProcessor.class));

        useCase.upload(frameProcessor, videoStream, s3Service);

        verify(databaseGateway).findByFilenameAndEmail(any(String.class), any(String.class));
        verify(s3Service).sendToHistoryBucket(any(String.class), any(String.class));
        verify(s3Service).sendToProcessBucket(any(String.class), any(String.class), any(String.class), any(String.class), any(Integer.class));
        verify(databaseGateway).create(any(FrameProcessor.class));

    }

    @Test
    void shouldThrowConflictExceptionWhenFileAlreadyExists() {

        when(databaseGateway.findByFilenameAndEmail(any(String.class), any(String.class))).thenReturn(Optional.of(getFrameProcessor()));

        assertThrows(ConflictException.class, () -> useCase.upload(getFrameProcessor(), new ByteArrayInputStream(new byte[0]), s3Service));

    }

    @Test
    void shouldThrowSaveVideoExceptionWhenErrorSavingVideo() {

        FrameProcessor frameProcessor = getFrameProcessor();
        ByteArrayInputStream videoStream = new ByteArrayInputStream(new byte[0]);

        when(databaseGateway.findByFilenameAndEmail(any(String.class), any(String.class))).thenReturn(Optional.empty());

        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.copy(any(InputStream.class), any(java.nio.file.Path.class))).thenThrow(IOException.class);

            assertThrows(SaveVideoException.class, () -> useCase.upload(frameProcessor, videoStream, s3Service));
        }
    }
}
