package com.snapit.bdd.frameprocessor;

import com.snapit.application.interfacegateway.FrameProcessorDatabaseGateway;
import com.snapit.application.usecase.frameprocessor.DownloadFramesUseCase;
import com.snapit.application.util.exception.ResourceNotFoundException;
import com.snapit.framework.aws.S3Service;
import com.snapit.util.FrameProcessorUtils;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.InputStreamResource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class DownloadFramesStepDefinitions {

    private DownloadFramesUseCase useCase;

    @Mock
    private FrameProcessorDatabaseGateway databaseGateway;

    @Mock
    private S3Service s3Service;

    private InputStreamResource resultado;
    private Exception excecao;

    AutoCloseable openMocks;

    @Before
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        this.useCase = new DownloadFramesUseCase(databaseGateway);
    }

    @Dado("que o banco de dados contém um arquivo de frames para o ID {string}")
    public void queOBancoDeDadosContemUmArquivoDeFrames(String id) {
        when(databaseGateway.findFileNameById(id)).thenReturn(Optional.of(FrameProcessorUtils.getFrameProcessor("123e4567-e89b-12d3-a456-426614174000")));
    }

    @Dado("o serviço S3 possui o arquivo correspondente")
    public void oServicoS3PossuiOArquivoCorrespondente() {
        InputStream arquivo = new ByteArrayInputStream(new byte[10]);
        when(s3Service.getFramesFile(anyString())).thenReturn(arquivo);
    }

    @Quando("o usuário solicita o download dos frames")
    public void oUsuarioSolicitaODownloadDosFrames() {
        try {
            resultado = useCase.downloadFrames("email@test.com", "123e4567-e89b-12d3-a456-426614174000", s3Service);
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Então("o download deve ser concluído com sucesso")
    public void oDownloadDeveSerConcluidoComSucesso() {
        assertNotNull(resultado);
    }

    @Dado("que o banco de dados não contém um arquivo de frames para o ID {string}")
    public void queOBancoDeDadosNaoContemUmArquivoDeFrames(String id) {
        when(databaseGateway.findFileNameById(id)).thenReturn(Optional.empty());
    }

    @Então("deve ser lançada uma exceção de recurso não encontrado")
    public void deveSerLancadaUmaExcecaoDeRecursoNaoEncontrado() {
        assertNotNull(excecao);
        assertInstanceOf(ResourceNotFoundException.class, excecao);
    }
}
