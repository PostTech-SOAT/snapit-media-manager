package com.snapit.framework.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.snapit.application.interfaces.BucketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class S3Service implements BucketService {

    private AmazonS3 s3Client;

    @Override
    public void sendToHistoryBucket(String filePath, String s3Key) {
        s3Client.putObject("snapit-history", s3Key, new File(filePath));
    }

    @Override
    public void sendToProcessBucket(String filePath, String s3Key, String id, String email, int frameInterval) {
        Map<String, String> metaList = new HashMap<>();
        metaList.put("id", id);
        metaList.put("email", email);
        metaList.put("frameinterval", String.valueOf(frameInterval));
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setUserMetadata(metaList);

        PutObjectRequest request = new PutObjectRequest("snapit-uploads", s3Key, new File(filePath));
        request.setMetadata(metadata);

        s3Client.putObject(request);
    }

    @Override
    public InputStream getOriginalFile(String s3Key) {
        return s3Client.getObject("snapit-history", s3Key).getObjectContent();
    }

    @Override
    public InputStream getFramesFile(String s3Key) {
        return s3Client.getObject("snapit-frames", s3Key).getObjectContent();
    }

}
