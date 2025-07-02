package com.avinashpatil.app.DishaComputerEducationHaladi.service;

import com.avinashpatil.app.DishaComputerEducationHaladi.dtos.ExamDto;

import java.util.List;

public interface ExamService {
    ExamDto createExam(ExamDto examDTO);
    ExamDto getExam(String examId);
    List<ExamDto> getExamsByBatch(String batchId);
    ExamDto updateExam(String examId, ExamDto examDTO);
    void deleteExam(String examId);
}
