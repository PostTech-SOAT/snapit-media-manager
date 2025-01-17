package com.snapit.framework.repository;

import com.snapit.framework.entity.EFrameProcessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FrameProcessorRepository extends JpaRepository<EFrameProcessor, String> {

    List<EFrameProcessor> findAllByEmail(String email);

}
