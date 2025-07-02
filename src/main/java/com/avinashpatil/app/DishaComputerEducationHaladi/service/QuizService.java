package com.avinashpatil.app.DishaComputerEducationHaladi.service;

import com.avinashpatil.app.DishaComputerEducationHaladi.dtos.*;
import com.avinashpatil.app.DishaComputerEducationHaladi.entity.QuizResult;

import java.util.List;

public interface QuizService {
    QuizDto createQuiz(QuizDto quizDTO);
    QuizDto getQuiz(String quizId);
    List<QuizDto> getQuizzesByBatch(String batchId);
}

