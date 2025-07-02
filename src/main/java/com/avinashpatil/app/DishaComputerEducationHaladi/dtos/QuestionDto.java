package com.avinashpatil.app.DishaComputerEducationHaladi.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuestionDto {
    private String questionId;

    @NotBlank(message = "English question is required")
    private String questionEnglish;

    @NotBlank(message = "Marathi question is required")
    private String questionMarathi;

    @NotBlank(message = "Option 1 English is required")
    private String option1English;

    @NotBlank(message = "Option 1 Marathi is required")
    private String option1Marathi;

    @NotBlank(message = "Option 2 English is required")
    private String option2English;

    @NotBlank(message = "Option 2 Marathi is required")
    private String option2Marathi;

    @NotBlank(message = "Option 3 English is required")
    private String option3English;

    @NotBlank(message = "Option 3 Marathi is required")
    private String option3Marathi;

    @NotBlank(message = "Option 4 English is required")
    private String option4English;

    @NotBlank(message = "Option 4 Marathi is required")
    private String option4Marathi;

    @NotNull(message = "Correct option is required")
    private Integer correctOption;

    @NotNull(message = "Marks are required")
    private Integer marks;

    private String quizId; // Optional, as questions can be created independently

    @NotBlank(message = "Course ID is required")
    private String courseId;
}
