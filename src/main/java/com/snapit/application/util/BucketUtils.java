package com.snapit.application.util;

public class BucketUtils {

    public static String getBucketKey(String email, String filename) {
        return email + "/" + filename;
    }
}
