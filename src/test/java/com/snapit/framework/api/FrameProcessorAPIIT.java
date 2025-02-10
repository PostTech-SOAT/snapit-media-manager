package com.snapit.framework.api;

import com.snapit.framework.aws.S3Service;
import com.snapit.framework.context.AuthenticationFilter;
import com.snapit.framework.repository.FrameProcessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.FileInputStream;
import java.io.InputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
class FrameProcessorAPIIT {

    private MockMvc mockMvc;

    @Mock
    private S3Service bucketService;

    @Autowired
    private FrameProcessorService service;

    @InjectMocks
    private FrameProcessorAPI frameProcessorAPI;

    @BeforeEach
    void setUp() {
        frameProcessorAPI = new FrameProcessorAPI(bucketService, service);
        mockMvc = MockMvcBuilders.standaloneSetup(frameProcessorAPI)
                .addFilter(new AuthenticationFilter(), "/*")
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();

    }

    @Test
    void shouldDownload() throws Exception {
        InputStream video = new FileInputStream("src/test/resources/video/dummy.mp4");

        when(bucketService.getFramesFile(any())).thenReturn(video);

        mockMvc.perform(get("/v1/frames/{id}/download", "38a92106-897f-44c2-a576-3715e223dd70")
                        .header("Authorization", "Bearer eyJraWQiOiJsQkhZQmVJU0RSOE1cLzVidjNMZFVaaFMrcDZVMVVlMlwvQThcL0RRZEVUSnZzPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiI5NGE4MDRiOC0yMDMxLTcwN2QtMTY4ZC1hOTljMDk3Y2I5ZTYiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV9BT0FDTERHbVYiLCJjbGllbnRfaWQiOiIybjQxZmxzbXVtbTJhM280OGNuZTQ5Zm1ybCIsIm9yaWdpbl9qdGkiOiJhMjliMDM3My0yMTZiLTRjMTktYjZjMy0zZDk2NThiYzY1OGIiLCJldmVudF9pZCI6IjMzMTk3YzRhLWEzNDQtNGQ4Yi05ZTRlLWYzYWUwMmY4MWU4NSIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoiYXdzLmNvZ25pdG8uc2lnbmluLnVzZXIuYWRtaW4iLCJhdXRoX3RpbWUiOjE3MzgwMTk4MzgsImV4cCI6MTczODAyMzQzNywiaWF0IjoxNzM4MDE5ODM4LCJqdGkiOiIyYzg0NzVkOS1lM2ViLTRjYTQtYmU0OC1mNzA5NTVlZmQ3YTIiLCJ1c2VybmFtZSI6Imd1aWFtYW9naWxAdGVzdC5jb20ifQ.T6LHOO0OPuriQcNgcFuFQVOdsfTOvJjMBVLPI2SMm3D8wjnBHvlvijYqiD4-HdQhGvajfusV24Tggfld8L_Df05LGzSJ7Lp9mugeAKc5J1NF8mIKkyvbNeib6g1JS8JuF5PK9IfEKXQh_dvIyqOLdL-CYM0_knBhrDpMGmP7HMI1wt8MGop-RUG4WcZTi8fr7H9InjFn8x1TZgDWdAOUAKEgpvOv5oJrjrJEANMakvl6Tep6iL4mhboIin8gtQ-CSYbfE9wjD2V3VB3K_5eDByEbOvKz8n7kt3SGg7SPkh0MRdBOx2IouabxuJWB0lcvdS4jx1k0vxln-HK3YvxbNQ")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldThrowExceptionWhenTryingToDownload() throws Exception {
        when(bucketService.getFramesFile(any())).thenReturn(new FileInputStream("src/test/resources/video/dummy.mp4"));

        mockMvc.perform(get("/v1/frames/{id}/download", "ebb24219-6b50-460e-80e2-24c341689d6e")
                        .header("Authorization", "Bearer eyJraWQiOiJsQkhZQmVJU0RSOE1cLzVidjNMZFVaaFMrcDZVMVVlMlwvQThcL0RRZEVUSnZzPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiI5NGE4MDRiOC0yMDMxLTcwN2QtMTY4ZC1hOTljMDk3Y2I5ZTYiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV9BT0FDTERHbVYiLCJjbGllbnRfaWQiOiIybjQxZmxzbXVtbTJhM280OGNuZTQ5Zm1ybCIsIm9yaWdpbl9qdGkiOiJhMjliMDM3My0yMTZiLTRjMTktYjZjMy0zZDk2NThiYzY1OGIiLCJldmVudF9pZCI6IjMzMTk3YzRhLWEzNDQtNGQ4Yi05ZTRlLWYzYWUwMmY4MWU4NSIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoiYXdzLmNvZ25pdG8uc2lnbmluLnVzZXIuYWRtaW4iLCJhdXRoX3RpbWUiOjE3MzgwMTk4MzgsImV4cCI6MTczODAyMzQzNywiaWF0IjoxNzM4MDE5ODM4LCJqdGkiOiIyYzg0NzVkOS1lM2ViLTRjYTQtYmU0OC1mNzA5NTVlZmQ3YTIiLCJ1c2VybmFtZSI6Imd1aWFtYW9naWxAdGVzdC5jb20ifQ.T6LHOO0OPuriQcNgcFuFQVOdsfTOvJjMBVLPI2SMm3D8wjnBHvlvijYqiD4-HdQhGvajfusV24Tggfld8L_Df05LGzSJ7Lp9mugeAKc5J1NF8mIKkyvbNeib6g1JS8JuF5PK9IfEKXQh_dvIyqOLdL-CYM0_knBhrDpMGmP7HMI1wt8MGop-RUG4WcZTi8fr7H9InjFn8x1TZgDWdAOUAKEgpvOv5oJrjrJEANMakvl6Tep6iL4mhboIin8gtQ-CSYbfE9wjD2V3VB3K_5eDByEbOvKz8n7kt3SGg7SPkh0MRdBOx2IouabxuJWB0lcvdS4jx1k0vxln-HK3YvxbNQ")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldThrowNotFoundWhenDownloadingFramesThatDontExists() throws Exception {
        mockMvc.perform(get("/v1/frames/{id}/download", "2430f756-e296-4e8a-950c-d1b4dd2717de")
                        .header("Authorization", "Bearer eyJraWQiOiJsQkhZQmVJU0RSOE1cLzVidjNMZFVaaFMrcDZVMVVlMlwvQThcL0RRZEVUSnZzPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiI5NGE4MDRiOC0yMDMxLTcwN2QtMTY4ZC1hOTljMDk3Y2I5ZTYiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV9BT0FDTERHbVYiLCJjbGllbnRfaWQiOiIybjQxZmxzbXVtbTJhM280OGNuZTQ5Zm1ybCIsIm9yaWdpbl9qdGkiOiJhMjliMDM3My0yMTZiLTRjMTktYjZjMy0zZDk2NThiYzY1OGIiLCJldmVudF9pZCI6IjMzMTk3YzRhLWEzNDQtNGQ4Yi05ZTRlLWYzYWUwMmY4MWU4NSIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoiYXdzLmNvZ25pdG8uc2lnbmluLnVzZXIuYWRtaW4iLCJhdXRoX3RpbWUiOjE3MzgwMTk4MzgsImV4cCI6MTczODAyMzQzNywiaWF0IjoxNzM4MDE5ODM4LCJqdGkiOiIyYzg0NzVkOS1lM2ViLTRjYTQtYmU0OC1mNzA5NTVlZmQ3YTIiLCJ1c2VybmFtZSI6Imd1aWFtYW9naWxAdGVzdC5jb20ifQ.T6LHOO0OPuriQcNgcFuFQVOdsfTOvJjMBVLPI2SMm3D8wjnBHvlvijYqiD4-HdQhGvajfusV24Tggfld8L_Df05LGzSJ7Lp9mugeAKc5J1NF8mIKkyvbNeib6g1JS8JuF5PK9IfEKXQh_dvIyqOLdL-CYM0_knBhrDpMGmP7HMI1wt8MGop-RUG4WcZTi8fr7H9InjFn8x1TZgDWdAOUAKEgpvOv5oJrjrJEANMakvl6Tep6iL4mhboIin8gtQ-CSYbfE9wjD2V3VB3K_5eDByEbOvKz8n7kt3SGg7SPkh0MRdBOx2IouabxuJWB0lcvdS4jx1k0vxln-HK3YvxbNQ")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldFindProcessingStatusByEmail() throws Exception {
        mockMvc.perform(get("/v1/frames/processor")
                        .header("Authorization", "Bearer eyJraWQiOiJsQkhZQmVJU0RSOE1cLzVidjNMZFVaaFMrcDZVMVVlMlwvQThcL0RRZEVUSnZzPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiI5NGE4MDRiOC0yMDMxLTcwN2QtMTY4ZC1hOTljMDk3Y2I5ZTYiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV9BT0FDTERHbVYiLCJjbGllbnRfaWQiOiIybjQxZmxzbXVtbTJhM280OGNuZTQ5Zm1ybCIsIm9yaWdpbl9qdGkiOiJhMjliMDM3My0yMTZiLTRjMTktYjZjMy0zZDk2NThiYzY1OGIiLCJldmVudF9pZCI6IjMzMTk3YzRhLWEzNDQtNGQ4Yi05ZTRlLWYzYWUwMmY4MWU4NSIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoiYXdzLmNvZ25pdG8uc2lnbmluLnVzZXIuYWRtaW4iLCJhdXRoX3RpbWUiOjE3MzgwMTk4MzgsImV4cCI6MTczODAyMzQzNywiaWF0IjoxNzM4MDE5ODM4LCJqdGkiOiIyYzg0NzVkOS1lM2ViLTRjYTQtYmU0OC1mNzA5NTVlZmQ3YTIiLCJ1c2VybmFtZSI6Imd1aWFtYW9naWxAdGVzdC5jb20ifQ.T6LHOO0OPuriQcNgcFuFQVOdsfTOvJjMBVLPI2SMm3D8wjnBHvlvijYqiD4-HdQhGvajfusV24Tggfld8L_Df05LGzSJ7Lp9mugeAKc5J1NF8mIKkyvbNeib6g1JS8JuF5PK9IfEKXQh_dvIyqOLdL-CYM0_knBhrDpMGmP7HMI1wt8MGop-RUG4WcZTi8fr7H9InjFn8x1TZgDWdAOUAKEgpvOv5oJrjrJEANMakvl6Tep6iL4mhboIin8gtQ-CSYbfE9wjD2V3VB3K_5eDByEbOvKz8n7kt3SGg7SPkh0MRdBOx2IouabxuJWB0lcvdS4jx1k0vxln-HK3YvxbNQ")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
