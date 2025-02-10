package com.snapit.framework.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class S3ServiceTest {

    @Mock
    private AmazonS3 amazonS3;

    private S3Service s3Service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        s3Service = new S3Service(amazonS3);
    }

    @Test
    void shouldSendToHistoryBucket() {
        assertDoesNotThrow(() -> s3Service.sendToHistoryBucket("src/test/resources/video/dummy.mp4",
                "dummy.mp4"));

        verify(amazonS3).putObject(any(String.class), any(String.class), any(File.class));
    }

    @Test
    void shouldGetOriginalFile() throws FileNotFoundException {
        S3Object object = new S3Object();
        object.setObjectContent(new FileInputStream("src/test/resources/video/dummy.mp4"));
        when(amazonS3.getObject(any(String.class), any(String.class))).thenReturn(object);

        assertEquals(object.getObjectContent(), s3Service.getOriginalFile("src/test/resources/video/dummy.mp4"));

        verify(amazonS3).getObject(any(String.class), any(String.class));
    }

    @Test
    void shouldGetFramesFile() throws FileNotFoundException {
        S3Object object = new S3Object();
        object.setObjectContent(new FileInputStream("src/test/resources/video/dummy.mp4"));
        when(amazonS3.getObject(any(String.class), any(String.class))).thenReturn(object);

        assertEquals(object.getObjectContent(), s3Service.getFramesFile("src/test/resources/video/dummy.mp4"));

        verify(amazonS3).getObject(any(String.class), any(String.class));
    }

    @Test
    void shouldSendToProcessBucket() {
        assertDoesNotThrow(() -> s3Service.sendToProcessBucket("src/test/resources/video/dummy.mp4",
                "dummy.mp4", UUID.randomUUID().toString(), "test@email.com", 30));

        verify(amazonS3).putObject(any(PutObjectRequest.class));
    }

}