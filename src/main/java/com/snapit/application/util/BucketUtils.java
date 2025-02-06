package com.snapit.application.util;

public class BucketUtils {

    private BucketUtils() {
    }

    public static String getBucketKey(String email, String filename) {
        return email + "/" + filename;
    }
}
