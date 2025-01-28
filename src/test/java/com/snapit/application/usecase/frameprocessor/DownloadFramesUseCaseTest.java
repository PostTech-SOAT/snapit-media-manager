package com.snapit.application.usecase.frameprocessor;

import com.snapit.application.interfacegateway.FrameProcessorDatabaseGateway;
import com.snapit.application.util.exception.ResourceNotFoundException;
import com.snapit.framework.aws.S3Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.InputStreamResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

import static com.snapit.util.FrameProcessorUtils.getFrameProcessor;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class DownloadFramesUseCaseTest {

    private DownloadFramesUseCase useCase;

    @Mock
    private FrameProcessorDatabaseGateway databaseGateway;

    @Mock
    private S3Service s3Service;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new DownloadFramesUseCase(databaseGateway);
    }

    @Test
    void shouldDownloadFrames() throws IOException {

        File dummyVideoZip = File.createTempFile("firstVideo", ".zip");
        dummyVideoZip.deleteOnExit();
        InputStream expectedFile = new FileInputStream(dummyVideoZip);

        when(databaseGateway.findFileNameById(any(String.class))).thenReturn(Optional.of(getFrameProcessor()));
        when(s3Service.getFramesFile(any(String.class))).thenReturn(expectedFile);

        InputStreamResource downloadedFrames = useCase.downloadFrames("email@test.com", UUID.randomUUID().toString(), s3Service);

        assertTrue(downloadedFrames.exists());

    }

    @Test
    void shouldThrowExceptionWhenFileNotFound() {

        when(databaseGateway.findFileNameById(any(String.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> useCase.downloadFrames("email@test.com", UUID.randomUUID().toString(), s3Service));

    }
}
