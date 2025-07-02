package com.avinashpatil.app.DishaComputerEducationHaladi.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResultDTO {
    private Long questionId;
    private String questionEn;
    private String questionMr;
    private String correctAnswer;
}
