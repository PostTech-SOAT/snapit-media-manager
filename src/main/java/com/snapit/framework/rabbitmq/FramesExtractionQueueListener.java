package com.snapit.framework.rabbitmq;

import com.snapit.framework.repository.FrameProcessorService;
import com.snapit.interfaceadaptors.controller.FrameProcessorController;
import com.snapit.interfaceadaptors.event.FramesExtractedEvent;
import com.snapit.interfaceadaptors.event.FramesExtractionFailedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FramesExtractionQueueListener {

    private final FrameProcessorService service;

    @RabbitListener(queues = "frames-extraction-finished-media-manager-queue")
    public void receiveMessage(FramesExtractedEvent event) {
        FrameProcessorController controller = new FrameProcessorController();
        controller.markFrameProcessorAsFinished(event.getId(), event.getFilename(), service);
    }

    @RabbitListener(queues = "frames-extraction-failed-media-manager-queue")
    public void receiveMessage(FramesExtractionFailedEvent event) {
        FrameProcessorController controller = new FrameProcessorController();
        controller.markFrameProcessorAsFailed(event.getId(), service);
    }

}
