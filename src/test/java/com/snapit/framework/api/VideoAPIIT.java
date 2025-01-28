package com.snapit.framework.api;

import com.snapit.framework.aws.S3Service;
import com.snapit.framework.repository.FrameProcessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@ActiveProfiles("test")
class VideoAPIIT {

    private MockMvc mockMvc;

    @Autowired
    private FrameProcessorService service;

    @Autowired
    private S3Service bucketService;

    @BeforeEach
    void setUp() {

        VideoAPI videoAPI = new VideoAPI(bucketService, service);
        mockMvc = MockMvcBuilders.standaloneSetup(videoAPI)
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();

    }

    @Test
    void shouldDownload() throws Exception {
        //todo
    }

    @Test
    void shouldUpload() throws Exception {
        //todo
    }
}
