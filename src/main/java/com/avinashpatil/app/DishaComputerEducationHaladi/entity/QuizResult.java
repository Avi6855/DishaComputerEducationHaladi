package com.avinashpatil.app.DishaComputerEducationHaladi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class QuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String quizName;
    private String categoryName;
    private String subcategoryName;
    private int totalQuestions;
    private int correctQuestions;
    private int incorrectQuestions;
}

