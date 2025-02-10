package com.snapit.bdd.frameprocessor;

import com.snapit.application.interfacegateway.FrameProcessorDatabaseGateway;
import com.snapit.application.usecase.frameprocessor.MarkFrameProcessorAsFinishedUseCase;
import com.snapit.domain.entity.VideoProcessingStatus;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MarkFrameProcessorAsFinishedStepsDefinitions {
    private MarkFrameProcessorAsFinishedUseCase useCase;

    @Mock
    private FrameProcessorDatabaseGateway databaseGateway;

    private String frameProcessorId;
    private String fileName;
    private Exception excecao;

    AutoCloseable openMocks;

    @Before
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new MarkFrameProcessorAsFinishedUseCase(databaseGateway);
    }

    @Dado("um processador de frames com ID válido")
    public void umProcessadorDeFramesComIDValido() {
        frameProcessorId = UUID.randomUUID().toString();
    }

    @Dado("um arquivo de vídeo processado chamado {string}")
    public void umArquivoDeVideoProcessadoChamado(String nomeArquivo) {
        fileName = nomeArquivo;
    }

    @Quando("o sistema marca o processador de frames como finalizado")
    public void oSistemaMarcaOProcessadorDeFramesComoFinalizado() {
        try {
            useCase.markFrameProcessorAsFinished(frameProcessorId, fileName);
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Então("o status do processador de frames deve ser atualizado para finalizado")
    public void oStatusDoProcessadorDeFramesDeveSerAtualizadoParaFinalizado() {
        verify(databaseGateway, times(1)).markProcessorAsFinished(
                eq(frameProcessorId),
                eq(VideoProcessingStatus.FINISHED),
                eq(fileName),
                any(LocalDateTime.class)
        );
        Assertions.assertNull(excecao);
    }
}
