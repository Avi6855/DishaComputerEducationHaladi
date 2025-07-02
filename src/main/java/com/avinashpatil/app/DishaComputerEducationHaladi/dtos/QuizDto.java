package com.avinashpatil.app.DishaComputerEducationHaladi.dtos;


import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class QuizDto {
    private String quizId;
    private String title;
    private int durationMinutes;
    private LocalDateTime scheduledTime;
    private boolean isSample;
    private String batchId;
    private List<QuestionDto> questions;
}
