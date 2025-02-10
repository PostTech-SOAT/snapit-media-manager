package com.snapit.framework.repository;

import com.snapit.framework.entity.EFrameProcessor;
import com.snapit.interfaceadaptors.gateway.repositorydto.FrameProcessorRepositoryDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.snapit.domain.entity.VideoProcessingStatus.PROCESSING;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FrameProcessorServiceTest {

    @Mock
    private FrameProcessorRepository repository;

    private FrameProcessorService service;

    private EFrameProcessor frameProcessor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new FrameProcessorService(repository);
        frameProcessor = new EFrameProcessor(UUID.randomUUID().toString(), "email@test.com",
                "dummy.mp4", 10, PROCESSING, LocalDateTime.now(), null,
                null);
    }

    @Test
    void should_find_processing_status_by_email() {
        when(repository.findAllByEmail(anyString())).thenReturn(singletonList(frameProcessor));
        assertEquals(singletonList(frameProcessor), service.findProcessingStatusByEmail("email@test.com"));

        verify(repository).findAllByEmail(anyString());
    }

    @Test
    void should_create() {
        assertDoesNotThrow(() -> service.create(new FrameProcessorRepositoryDTO(UUID.randomUUID().toString(),
                "email@test.com", "dummy.mp4", 10, PROCESSING, LocalDateTime.now())));

        verify(repository).save(any(EFrameProcessor.class));
    }

    @Test
    void should_mark_frame_processor_as_finished() {
        String id = UUID.randomUUID().toString();
        LocalDateTime finishedAt = LocalDateTime.now();
        assertDoesNotThrow(() -> service.markFrameProcessorAsFinished(id, PROCESSING, "dummy.mp4",
                finishedAt));

        verify(repository).markFrameProcessorAsFinished(id, PROCESSING.toString(), "dummy.mp4", finishedAt);
    }

    @Test
    void should_mark_frame_processor_as_failed() {
        String id = UUID.randomUUID().toString();
        LocalDateTime finishedAt = LocalDateTime.now();
        assertDoesNotThrow(() -> service.markFrameProcessorAsFailed(id, PROCESSING, finishedAt));

        verify(repository).markFrameProcessorAsFailed(id, PROCESSING.toString(), finishedAt);
    }

    @Test
    void should_find_file_name_by_id() {
        when(repository.findById(frameProcessor.getId())).thenReturn(Optional.of(frameProcessor));
        assertEquals(Optional.of(frameProcessor), service.findFileNameById(frameProcessor.getId()));

        verify(repository).findById(frameProcessor.getId());
    }

    @Test
    void should_find_by_file_name_and_id() {
        when(repository.findByOriginalFilenameAndEmail(frameProcessor.getOriginalFilename(), frameProcessor.getEmail()))
                .thenReturn(Optional.of(frameProcessor));
        assertEquals(Optional.of(frameProcessor), service.findByFilenameAndEmail(frameProcessor.getOriginalFilename(),
                frameProcessor.getEmail()));

        verify(repository).findByOriginalFilenameAndEmail(frameProcessor.getOriginalFilename(), frameProcessor.getEmail());
    }

}