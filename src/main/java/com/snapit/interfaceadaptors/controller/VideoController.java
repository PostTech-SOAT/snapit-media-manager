package com.snapit.interfaceadaptors.controller;

import com.snapit.application.usecase.video.DownloadVideoUseCase;
import com.snapit.application.usecase.video.UploadVideoUseCase;

public class VideoController {

    public void upload() {
        UploadVideoUseCase useCase = new UploadVideoUseCase();
        useCase.upload();
    }

    public void download() {
        DownloadVideoUseCase useCase = new DownloadVideoUseCase();
        useCase.download();
    }
}
