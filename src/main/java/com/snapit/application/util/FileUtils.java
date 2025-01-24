package com.snapit.application.util;

import java.io.File;

public class FileUtils {

    public static void deleteFiles(String dirToDelete) {
        File file = new File(dirToDelete);
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteFile(f);
            }
        }
        deleteFile(file);
    }

    private static void deleteFile(File file) {
        if (!file.delete()) {
            System.err.println("Failed to delete file: " + file.getAbsolutePath());
        }
    }

}
