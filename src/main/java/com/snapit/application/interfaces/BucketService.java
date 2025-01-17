package com.snapit.application.interfaces;

import java.io.IOException;

public interface BucketService {

    void sendToHistoryBucket(String filePath, String s3Key);

    void sendToProcessBucket(String filePath, String s3Key, String email, int frameInterval);

    byte[] getOriginalFile(String s3Key) throws IOException;

    byte[] getFramesFile(String s3Key) throws IOException;
}
