package com.snapit.application.util;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    @Test
    void shouldDeleteFilesSuccessfully() throws IOException {
        File tempDir = new File(System.getProperty("java.io.tmpdir"), "tempDir");
        tempDir.mkdir();
        File tempFile1 = new File(tempDir, "tempFile1.txt");
        File tempFile2 = new File(tempDir, "tempFile2.txt");
        tempFile1.createNewFile();
        tempFile2.createNewFile();

        assertTrue(tempFile1.exists());
        assertTrue(tempFile2.exists());
        assertTrue(tempDir.exists());

        FileUtils.deleteFiles(tempDir.getAbsolutePath());

        assertFalse(tempFile1.exists());
        assertFalse(tempFile2.exists());
        assertFalse(tempDir.exists());
    }

    @Test
    void shouldFailToDeleteNonExistentFile() {
        File nonExistentFile = new File(System.getProperty("java.io.tmpdir"), "nonExistentFile.txt");

        assertFalse(nonExistentFile.exists());

        FileUtils.deleteFiles(nonExistentFile.getAbsolutePath());

        assertFalse(nonExistentFile.exists());
    }

    @Test
    void shouldGetVideoName() {
        String input = "InputStream resource [firstVideo.mp4]";

        String videoName = FileUtils.getVideoName(input);

        assertEquals("firstVideo.mp4", videoName);
    }
}
