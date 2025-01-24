package com.snapit.application.interfaces;

import java.io.IOException;
import java.io.InputStream;

public interface BucketService {

    void sendToHistoryBucket(String filePath, String s3Key);

    void sendToProcessBucket(String filePath, String s3Key, String email, int frameInterval);

    InputStream getOriginalFile(String s3Key);

    byte[] getFramesFile(String s3Key) throws IOException;
}
