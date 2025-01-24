package com.snapit.application.usecase.video;

import com.snapit.application.interfaces.BucketService;

import java.io.InputStream;

import static com.snapit.application.util.BucketUtils.getBucketKey;

public class DownloadVideoUseCase {

    public InputStream download(BucketService service, String email, String filename) {
        return service.getOriginalFile(getBucketKey(email, filename));
    }

}
