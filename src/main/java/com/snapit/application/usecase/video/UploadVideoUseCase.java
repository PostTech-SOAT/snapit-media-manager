package com.snapit.application.usecase.video;

import com.snapit.application.interfacegateway.FrameProcessorDatabaseGateway;
import com.snapit.application.interfaces.BucketService;
import com.snapit.application.util.FileUtils;
import com.snapit.application.util.exception.ConflictException;
import com.snapit.application.util.exception.SaveVideoException;
import com.snapit.domain.entity.FrameProcessor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static com.snapit.application.util.BucketUtils.getBucketKey;

public class UploadVideoUseCase {

    private final FrameProcessorDatabaseGateway databaseGateway;

    public UploadVideoUseCase(FrameProcessorDatabaseGateway databaseGateway) {
        this.databaseGateway = databaseGateway;
    }

    public void upload(FrameProcessor frameProcessor, InputStream video, BucketService service) {
        if(databaseGateway.findByFilenameAndEmail(frameProcessor.getOriginalFilename(), frameProcessor.getEmail()).isPresent()) {
            throw new ConflictException("File already exists");
        }

        String filePathname = frameProcessor.getEmail() + "-" + frameProcessor.getOriginalFilename().substring(0, frameProcessor.getOriginalFilename().lastIndexOf('.'));
        this.createFolder(filePathname);
        this.saveVideo(video, filePathname, frameProcessor.getOriginalFilename());

        String filePath = filePathname + File.separator + frameProcessor.getOriginalFilename();
        service.sendToHistoryBucket(filePath, getBucketKey(frameProcessor.getEmail(), frameProcessor.getOriginalFilename()));
        service.sendToProcessBucket(filePath, frameProcessor.getOriginalFilename(), frameProcessor.getId(), frameProcessor.getEmail(),
                frameProcessor.getFrameInterval());
        FileUtils.deleteFiles(filePathname);

        databaseGateway.create(frameProcessor);
    }

    private void createFolder(String filePathname) {
        new File(filePathname).mkdir();
    }

    private void saveVideo(InputStream video, String filePathname, String filename) {
        try {
            Files.copy(video, new File(filePathname + File.separator + filename).toPath());
        } catch (IOException e) {
            throw new SaveVideoException("Error saving video: " + e.getMessage());
        }
    }
}
