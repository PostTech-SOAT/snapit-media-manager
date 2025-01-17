package com.snapit.framework.rabbitmq;

import com.snapit.interfaceadaptors.event.FramesExtractedEvent;
import com.snapit.interfaceadaptors.event.FramesExtractionFailedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FramesExtractionQueueListener {
    //colocar as dependencias que serão injetadas

    @RabbitListener(queues = "frames-extraction-finished-media-manager-queue")
    public void receiveMessage(FramesExtractedEvent framesExtractedEvent) {
        // inicializar o controller
        // chamar o método do controller responsável por chamar o use case de sucesso
    }

    @RabbitListener(queues = "frames-extraction-finished-media-manager-queue")
    public void receiveMessage(FramesExtractionFailedEvent framesExtractionFailedEvent) {
        // inicializar o controller
        // chamar o método do controller responsável por chamar o use case de sucesso
    }

}
