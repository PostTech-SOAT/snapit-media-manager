package com.snapit.application.usecase.video;

import com.snapit.application.interfacegateway.FrameProcessorDatabaseGateway;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class DownloadVideoUseCaseTest {

    private DownloadVideoUseCase useCase;

    @Mock
    private FrameProcessorDatabaseGateway databaseGateway;

    @Mock
    private S3Service s3Service;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new DownloadVideoUseCase(databaseGateway);
    }

    @Test
    void shouldDownload() throws IOException {

        File dummyVideo = File.createTempFile("firstVideo", ".mp4");
        dummyVideo.deleteOnExit();
        InputStream expectedFile = new FileInputStream(dummyVideo);

        when(databaseGateway.findFileNameById(any(String.class))).thenReturn(Optional.of(getFrameProcessor()));
        when(s3Service.getOriginalFile(any(String.class))).thenReturn(expectedFile);

        InputStreamResource downloadedFrames = useCase.download("email@test.com", UUID.randomUUID().toString(), s3Service);

        assertTrue(downloadedFrames.exists());

    }

}
