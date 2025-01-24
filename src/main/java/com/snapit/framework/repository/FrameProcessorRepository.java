package com.snapit.framework.repository;

import com.snapit.framework.entity.EFrameProcessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FrameProcessorRepository extends JpaRepository<EFrameProcessor, String> {

    List<EFrameProcessor> findAllByEmail(String email);

    @Modifying
    @Query(value = "UPDATE frame_processor SET status = :status, frames_filename = :framesFilename, " +
            "finished_at = :finishedAt WHERE id = :id", nativeQuery = true)
    void markFrameProcessorAsFinished(String id, String status, String framesFilename, LocalDateTime finishedAt);

    @Modifying
    @Query(value = "UPDATE frame_processor SET status = :status, finished_at = :finishedAt WHERE " +
            "id = :id", nativeQuery = true)
    void markFrameProcessorAsFailed(String id, String status, LocalDateTime finishedAt);

}
