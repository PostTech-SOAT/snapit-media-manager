package com.snapit.bdd.frameprocessor;

import com.snapit.application.interfacegateway.FrameProcessorDatabaseGateway;
import com.snapit.application.usecase.frameprocessor.MarkFrameProcessorAsFailedUseCase;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class MarkFrameProcessorAsFailedStepsDefinitions {

    private MarkFrameProcessorAsFailedUseCase useCase;

    @Mock
    private FrameProcessorDatabaseGateway databaseGateway;

    AutoCloseable openMocks;

    @Before
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new MarkFrameProcessorAsFailedUseCase(databaseGateway);
    }

    @Dado("que um vídeo está sendo processado")
    public void queUmVideoEstaSendoProcessado() {
        doNothing().when(databaseGateway).markProcessorAsFailed(anyString(), any(), any());
    }

    @Quando("ocorre uma falha no processamento")
    public void ocorreUmaFalhaNoProcessamento() {
        useCase.markFrameProcessorAsFailed("123e4567-e89b-12d3-a456-426614174000");
    }

    @Então("o sistema deve marcar o processamento como falhado")
    public void oSistemaDeveMarcarOProcessamentoComoFalhado() {
        verify(databaseGateway, times(1)).markProcessorAsFailed(anyString(), any(), any());
    }
}
