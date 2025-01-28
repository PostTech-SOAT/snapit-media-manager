package com.snapit.framework.api;

import com.snapit.framework.aws.S3Service;
import com.snapit.framework.repository.FrameProcessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
class FrameProcessorAPIIT {

    private MockMvc mockMvc;

    @Autowired
    private FrameProcessorService service;

    @Mock
    private S3Service bucketService;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {

        openMocks = MockitoAnnotations.openMocks(this);

        FrameProcessorAPI frameProcessorAPI = new FrameProcessorAPI(bucketService, service);
        mockMvc = MockMvcBuilders.standaloneSetup(frameProcessorAPI)
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();

    }

    @Test
    void shouldDownload() throws Exception {
        /*File dummyVideo = File.createTempFile("firstVideo", ".mp4");
        dummyVideo.deleteOnExit();
        InputStream expectedFile = new FileInputStream(dummyVideo);

        when(bucketService.getFramesFile("505a4a25-80c6-4a55-9850-6079e4740ab6")).thenReturn(expectedFile);

        mockMvc.perform(get("/v1/frames/{id}/download", "505a4a25-80c6-4a55-9850-6079e4740ab6")
                        .header("userEmail", "email@test.com")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());*/
    }

    @Test
    void shouldThrowNotFoundWhenDownloadingFramesThatDontExists() throws Exception {
        mockMvc.perform(get("/v1/frames/{id}/download", "2430f756-e296-4e8a-950c-d1b4dd2717de")
                        .header("userEmail", "email@test.com")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldFindProcessingStatusByEmail() throws Exception {
        mockMvc.perform(get("/v1/frames/processor")
                        .header("userEmail", "email@test.com")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
