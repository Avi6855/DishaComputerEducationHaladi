package com.avinashpatil.app.DishaComputerEducationHaladi.service.Impl;

import com.avinashpatil.app.DishaComputerEducationHaladi.dtos.ExamDto;
import com.avinashpatil.app.DishaComputerEducationHaladi.entity.Batch;
import com.avinashpatil.app.DishaComputerEducationHaladi.entity.Exam;
import com.avinashpatil.app.DishaComputerEducationHaladi.exceptions.ResourceNotFoundException;
import com.avinashpatil.app.DishaComputerEducationHaladi.repositories.BatchRepository;
import com.avinashpatil.app.DishaComputerEducationHaladi.repositories.ExamRepository;
import com.avinashpatil.app.DishaComputerEducationHaladi.service.ExamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableScheduling
public class ExamServiceImpl implements ExamService {
    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ExamDto createExam(ExamDto examDTO) {
        Batch batch = batchRepository.findById(examDTO.getBatchId())
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found with ID: " + examDTO.getBatchId()));

        Exam exam = modelMapper.map(examDTO, Exam.class);
        exam.setBatch(batch);
        Exam savedExam = examRepository.save(exam);

        // Immediate notification
        batch.getStudents().forEach(student ->
                notificationService.notifyUser(
                        student.getUserId(),
                        "New exam '" + exam.getTitle() + "' scheduled for " + exam.getScheduledDateTime() + " in batch " + batch.getBatchNo()
                )
        );

        return modelMapper.map(savedExam, ExamDto.class);
    }

    @Override
    public ExamDto getExam(String examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found with ID: " + examId));
        return modelMapper.map(exam, ExamDto.class);
    }

    @Override
    public List<ExamDto> getExamsByBatch(String batchId) {
        return examRepository.findByBatchBatchId(batchId).stream()
                .map(exam -> modelMapper.map(exam, ExamDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ExamDto updateExam(String examId, ExamDto examDTO) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found with ID: " + examId));
        modelMapper.map(examDTO, exam);
        Exam updatedExam = examRepository.save(exam);

        notificationService.notifyBatchUpdate(
                exam.getBatch().getBatchId(),
                "Exam '" + updatedExam.getTitle() + "' has been updated to " + updatedExam.getScheduledDateTime()
        );

        return modelMapper.map(updatedExam, ExamDto.class);
    }

    @Override
    public void deleteExam(String examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found with ID: " + examId));
        examRepository.delete(exam);
    }

    @Scheduled(fixedRate = 60000) // Check every minute
    public void notifyUpcomingExams() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reminderTime = now.plusMinutes(30); // Notify 30 minutes before

        List<Exam> upcomingExams = examRepository.findByScheduledDateTimeBetween(now, reminderTime);
        for (Exam exam : upcomingExams) {
            Batch batch = exam.getBatch();
            batch.getStudents().forEach(student ->
                    notificationService.notifyUser(
                            student.getUserId(),
                            "Reminder: Exam '" + exam.getTitle() + "' is scheduled for " + exam.getScheduledDateTime() +
                                    " (in less than 30 minutes) in batch " + batch.getBatchNo()
                    )
            );
        }
    }
}
