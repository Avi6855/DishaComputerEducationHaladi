package com.avinashpatil.app.DishaComputerEducationHaladi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ExamDto {
    private String examId;

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Scheduled date and time are required")
    private LocalDateTime scheduledDateTime;

    @NotNull(message = "Duration is required")
    private Integer durationMinutes;

    private boolean isSample;

    @NotBlank(message = "Batch ID is required")
    private String batchId;

    private List<String> questionIds; // List of question IDs for the exam
}
