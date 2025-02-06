package com.snapit.bdd.frameprocessor;

import com.snapit.application.interfacegateway.FrameProcessorDatabaseGateway;
import com.snapit.application.usecase.frameprocessor.FindVideoProcessingStatusUseCase;
import com.snapit.domain.entity.FrameProcessor;
import com.snapit.util.FrameProcessorUtils;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class FindVideoProcessingStatusStepDefinitions {

    private FindVideoProcessingStatusUseCase useCase;

    @Mock
    private FrameProcessorDatabaseGateway databaseGateway;

    private List<FrameProcessor> resultado;

    AutoCloseable openMocks;

    @Before
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new FindVideoProcessingStatusUseCase(databaseGateway);
    }

    @Dado("que existem vídeos processados e em processamento para o e-mail {string}")
    public void queExistemVideosProcessados(String email) {
        when(databaseGateway.findProcessingStatusByEmail(email))
                .thenReturn(List.of(FrameProcessorUtils.getFrameProcessor(), FrameProcessorUtils.getFrameProcessor()));
    }

    @Quando("o usuário consulta o status de processamento")
    public void oUsuarioConsultaOStatusDeProcessamento() {
        resultado = useCase.findProcessingStatusByEmail("email@test.com");
    }

    @Então("o sistema deve retornar os status dos vídeos")
    public void oSistemaDeveRetornarOsStatusDosVideos() {
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
    }
}
