package com.avinashpatil.app.DishaComputerEducationHaladi.service;


import com.avinashpatil.app.DishaComputerEducationHaladi.dtos.QuestionDto;
import com.avinashpatil.app.DishaComputerEducationHaladi.dtos.QuestionDto;

import java.util.List;

public interface QuestionService {
    QuestionDto createQuestion(QuestionDto questionDTO);
    QuestionDto getQuestion(String questionId);
    List<QuestionDto> getQuestionsByCourse(String courseId);
    QuestionDto updateQuestion(String questionId, QuestionDto questionDTO);
    void deleteQuestion(String questionId);
    List<QuestionDto> getQuestionsByQuiz(String quizId);
    void addQuestionToQuiz(String quizId, String questionId);
}
