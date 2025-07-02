package com.avinashpatil.app.DishaComputerEducationHaladi.repositories;

import com.avinashpatil.app.DishaComputerEducationHaladi.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam,String> {
    Collection<Object> findByBatchBatchId(String batchId);

    List<Exam> findByScheduledDateTimeBetween(LocalDateTime now, LocalDateTime reminderTime);
}
