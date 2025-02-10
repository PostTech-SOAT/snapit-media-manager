package com.snapit.framework.api;

import com.snapit.framework.aws.S3Service;
import com.snapit.framework.context.ContextHolder;
import com.snapit.framework.repository.FrameProcessorService;
import com.snapit.interfaceadaptors.controller.VideoController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

class VideoAPITest {

    @Mock
    private S3Service bucketService;

    @Mock
    private FrameProcessorService frameService;

    @Mock
    private VideoController videoController;

    @Mock
    private MultipartFile videoFile;

    @InjectMocks
    private VideoAPI videoAPI;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        videoAPI = new VideoAPI(bucketService, frameService);
        videoController = mock(VideoController.class);
    }

    @Test
    void shouldUpload() throws IOException {
        ContextHolder.setEmail("test@email.com");

        when(videoFile.getInputStream()).thenReturn(new ByteArrayInputStream("fake-video-content".getBytes()));
        when(videoFile.getOriginalFilename()).thenReturn("testVideo.mp4");
        doNothing().when(videoController).upload(anyInt(), any(), any(), any(), any(), any());

        ResponseEntity<Object> response = videoAPI.upload(10, videoFile);

        assertEquals(OK, response.getStatusCode());
    }

    @Test
    void testUploadIOException() throws IOException {
        when(videoFile.getInputStream()).thenThrow(new IOException("IO Exception"));

        ResponseEntity<Object> response = videoAPI.upload(10, videoFile);

        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertEquals("IO Exception", response.getBody());
    }

}