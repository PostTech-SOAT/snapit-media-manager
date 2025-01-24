package com.snapit.application.util;

public class S3Utils {

    public static String getS3Key(String email, String filename) {
        return email + "/" + filename;
    }
}
