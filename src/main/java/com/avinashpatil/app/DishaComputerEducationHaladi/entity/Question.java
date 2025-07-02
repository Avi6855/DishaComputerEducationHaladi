package com.avinashpatil.app.DishaComputerEducationHaladi.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String questionId;

    private String questionEnglish;
    private String questionMarathi;
    private String option1English;
    private String option1Marathi;
    private String option2English;
    private String option2Marathi;
    private String option3English;
    private String option3Marathi;
    private String option4English;
    private String option4Marathi;
    private int correctOption;
    private int marks;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}


