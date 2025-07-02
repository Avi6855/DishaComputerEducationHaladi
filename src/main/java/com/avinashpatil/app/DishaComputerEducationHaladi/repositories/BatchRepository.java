package com.avinashpatil.app.DishaComputerEducationHaladi.repositories;

import com.avinashpatil.app.DishaComputerEducationHaladi.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch, String> {
    List<Batch> findByCourseCourseId(String courseId);
}
