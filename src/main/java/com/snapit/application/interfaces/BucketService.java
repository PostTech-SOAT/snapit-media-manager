package com.snapit.application.interfaces;

import java.io.IOException;
import java.io.InputStream;

public interface BucketService {

    void sendToHistoryBucket(String filePath, String bucketKey);

    void sendToProcessBucket(String filePath, String bucketKey, String id, String email, int frameInterval);

    InputStream getOriginalFile(String bucketKey);

    InputStream getFramesFile(String bucketKey) throws IOException;
}
