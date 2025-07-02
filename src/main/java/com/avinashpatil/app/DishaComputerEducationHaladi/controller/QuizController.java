package com.avinashpatil.app.DishaComputerEducationHaladi.controller;

import com.avinashpatil.app.DishaComputerEducationHaladi.dtos.QuizDto;
import com.avinashpatil.app.DishaComputerEducationHaladi.service.QuizService;
import com.avinashpatil.app.DishaComputerEducationHaladi.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuestionService questionService;

    @PostMapping
    public ResponseEntity<QuizDto> createQuiz(@Valid @RequestBody QuizDto quizDTO) {
        QuizDto createdQuiz = quizService.createQuiz(quizDTO);
        return ResponseEntity.ok(createdQuiz);
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<QuizDto> getQuiz(@PathVariable String quizId) {
        QuizDto quiz = quizService.getQuiz(quizId);
        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/batch/{batchId}")
    public ResponseEntity<List<QuizDto>> getQuizzesByBatch(@PathVariable String batchId) {
        List<QuizDto> quizzes = quizService.getQuizzesByBatch(batchId);
        return ResponseEntity.ok(quizzes);
    }
/*
    @PutMapping("/{quizId}")
    public ResponseEntity<QuizDTO> updateQuiz(
            @PathVariable String quizId,
            @Valid @RequestBody QuizDTO quizDTO) {
        QuizDto updatedQuiz = quizService.updateQuiz(quizId, quizDTO);
        return ResponseEntity.ok(updatedQuiz);
    }

    @DeleteMapping("/{quizId}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable String quizId) {
        quizService.deleteQuiz(quizId);
        return ResponseEntity.ok().build();
    }*/

    @PostMapping("/{quizId}/questions/{questionId}")
    public ResponseEntity<Void> addQuestionToQuiz(
            @PathVariable String quizId,
            @PathVariable String questionId) {
        questionService.addQuestionToQuiz(quizId, questionId);
        return ResponseEntity.ok().build();
    }
}