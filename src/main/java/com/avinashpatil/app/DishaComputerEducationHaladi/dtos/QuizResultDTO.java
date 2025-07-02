package com.avinashpatil.app.DishaComputerEducationHaladi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizResultDTO {
    private int totalQuestions;
    private int correctAnswers;
    private int incorrectAnswers;
    private List<QuestionResultDTO> questionResults;
}
