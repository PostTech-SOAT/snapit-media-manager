package com.snapit.bdd.video;

import com.snapit.application.interfacegateway.FrameProcessorDatabaseGateway;
import com.snapit.application.usecase.video.DownloadVideoUseCase;
import com.snapit.domain.entity.FrameProcessor;
import com.snapit.framework.aws.S3Service;
import com.snapit.util.FrameProcessorUtils;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import lombok.SneakyThrows;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.InputStreamResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class DownloadVideoStepDefinitions {
    private DownloadVideoUseCase useCase;

    @Mock
    private FrameProcessorDatabaseGateway databaseGateway;

    @Mock
    private S3Service s3Service;

    private InputStreamResource videoRetornado;

    Exception excecao;

    AutoCloseable openMocks;

    @Before
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new DownloadVideoUseCase(databaseGateway);
    }

    @Dado("que existe um vídeo salvo para o ID {string}")
    @SneakyThrows
    public void queExisteUmVideoSalvoParaOID(String videoId) {
        FrameProcessor frameProcessor = FrameProcessorUtils.getFrameProcessor("123e4567-e89b-12d3-a456-426614174000");

        File dummyVideo = File.createTempFile("video", ".mp4");
        dummyVideo.deleteOnExit();
        InputStream expectedFile = new FileInputStream(dummyVideo);

        when(databaseGateway.findFileNameById(videoId)).thenReturn(Optional.of(frameProcessor));
        when(s3Service.getOriginalFile(any(String.class))).thenReturn(expectedFile);
    }

    @Quando("o usuário solicita o download do vídeo")
    public void oUsuarioSolicitaODownloadDoVideo() {
        try {
            videoRetornado = useCase.download("email@test.com", "123e4567-e89b-12d3-a456-426614174000", s3Service);
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Entao("o vídeo deve ser retornado com sucesso")
    public void oVideoDeveSerRetornadoComSucesso() {
        assertNotNull(videoRetornado, "O vídeo retornado é nulo!");
        assertTrue(videoRetornado.exists());
    }
}
