package com.snapit.interfaceadaptors.presenter;

import com.snapit.domain.entity.FrameProcessor;
import com.snapit.interfaceadaptors.dto.FrameProcessorDTO;
import org.junit.jupiter.api.Test;

import static com.snapit.domain.entity.VideoProcessingStatus.FINISHED;
import static com.snapit.util.FrameProcessorUtils.getFrameProcessor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FrameProcessorPresenterTest {

    @Test
    void testToDTO() {
        FrameProcessor frameProcessor = getFrameProcessor();

        FrameProcessorDTO dto = FrameProcessorPresenter.toDTO(frameProcessor);

        assertNotNull(dto.getId());
        assertEquals("email@test.com", dto.getEmail());
        assertEquals("firstVideo.mp4", dto.getOriginalFilename());
        assertEquals(FINISHED, dto.getStatus());
    }
}