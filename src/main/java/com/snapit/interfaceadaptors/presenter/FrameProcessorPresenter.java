package com.snapit.interfaceadaptors.presenter;

import com.snapit.domain.entity.FrameProcessor;
import com.snapit.interfaceadaptors.dto.FrameProcessorDTO;

public class FrameProcessorPresenter {

    public static FrameProcessorDTO toDTO(FrameProcessor frameProcessor) {
        return new FrameProcessorDTO(frameProcessor.getId(), frameProcessor.getEmail(), frameProcessor.getOriginalFilename(), frameProcessor.getStatus());
    }
}
