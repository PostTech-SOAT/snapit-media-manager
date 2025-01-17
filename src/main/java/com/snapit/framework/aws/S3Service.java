package com.snapit.framework.aws;

import com.snapit.application.interfaces.BucketService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.amazonaws.util.IOUtils.toByteArray;

@Service
@AllArgsConstructor
public class S3Service implements BucketService {

    private AmazonS3 s3Client;

    @Override
    public void sendToHistoryBucket(String filePath, String s3Key) {
        s3Client.putObject("snapit-history", s3Key, new File(filePath));
    }

    @Override
    public void sendToProcessBucket(String filePath, String s3Key, String email, int frameInterval) {
        Map<String, String> metaList = new HashMap<>();
        metaList.put("email", email);
        metaList.put("frameinterval", String.valueOf(frameInterval));
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setUserMetadata(metaList);

        PutObjectRequest request = new PutObjectRequest("snapit-uploads", s3Key, new File(filePath));
        request.setMetadata(metadata);

        s3Client.putObject(request);
    }

    @Override
    public byte[] getOriginalFile(String s3Key) throws IOException {
        S3Object s3Object = s3Client.getObject("snapit-history", s3Key);
        return toByteArray(s3Object.getObjectContent());
    }

    @Override
    public byte[] getFramesFile(String s3Key) throws IOException {
        S3Object s3Object = s3Client.getObject("snapit-frames", s3Key);
        return toByteArray(s3Object.getObjectContent());
    }

}
