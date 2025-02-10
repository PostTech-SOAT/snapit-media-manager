package com.snapit.bdd.video;

import com.snapit.application.interfacegateway.FrameProcessorDatabaseGateway;
import com.snapit.application.usecase.video.UploadVideoUseCase;
import com.snapit.application.util.exception.ConflictException;
import com.snapit.domain.entity.FrameProcessor;
import com.snapit.framework.aws.S3Service;
import com.snapit.util.FrameProcessorUtils;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UploadVideoStepDefinitions {

    private UploadVideoUseCase useCase;

    @Mock
    private FrameProcessorDatabaseGateway databaseGateway;

    @Mock
    private S3Service s3Service;

    private Exception excecao;

    AutoCloseable openMocks;

    @Before
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new UploadVideoUseCase(databaseGateway);
    }

    @Dado("que um vídeo válido é enviado para upload")
    public void queUmVideoValidoEEnviadoParaUpload() {
        when(databaseGateway.findByFilenameAndEmail(anyString(), anyString())).thenReturn(Optional.empty());
        doNothing().when(databaseGateway).create(any());
    }

    @Quando("o usuário faz o upload")
    public void oUsuarioFazOUpload() {
        try {
            useCase.upload(FrameProcessorUtils.getFrameProcessor(), new ByteArrayInputStream(new byte[0]), s3Service);
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Então("o sistema deve armazenar o vídeo e iniciar o processamento")
    public void oSistemaDeveArmazenarOVideoEIniciarOProcessamento() {
        verify(databaseGateway, times(1)).create(any());
    }

    @Dado("que já existe um vídeo com o mesmo nome para o e-mail {string}")
    public void queJaExisteUmVideoComOMesmoNomeParaOEmail(String email) {
        FrameProcessor frameProcessor = new FrameProcessor("id-video", email, "meu_video.mp4", 10, null, null, null, null);
        when(databaseGateway.findByFilenameAndEmail(any(String.class), any(String.class)))
                .thenReturn(Optional.of(frameProcessor));
    }

    @Quando("o usuário tenta fazer o upload novamente")
    public void oUsuarioTentaFazerOUploadNovamente() {
        try {
            useCase.upload(FrameProcessorUtils.getFrameProcessor(), new ByteArrayInputStream(new byte[0]), s3Service);
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Entao("deve ser lançada uma exceção de conflito")
    public void deveSerLancadaUmaExcecaoDeConflito() {
        Assertions.assertInstanceOf(ConflictException.class, excecao);
    }
}
