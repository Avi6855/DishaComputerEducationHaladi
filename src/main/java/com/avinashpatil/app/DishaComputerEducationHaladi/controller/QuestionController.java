package com.avinashpatil.app.DishaComputerEducationHaladi.controller;


import com.avinashpatil.app.DishaComputerEducationHaladi.dtos.QuestionDto;
import com.avinashpatil.app.DishaComputerEducationHaladi.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping
    public ResponseEntity<QuestionDto> createQuestion(@Valid @RequestBody QuestionDto questionDTO) {
        QuestionDto createdQuestion = questionService.createQuestion(questionDTO);
        return ResponseEntity.ok(createdQuestion);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDto> getQuestion(@PathVariable String questionId) {
        QuestionDto question = questionService.getQuestion(questionId);
        return ResponseEntity.ok(question);
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<QuestionDto>> getQuestionsByQuiz(@PathVariable String quizId) {
        List<QuestionDto> questions = questionService.getQuestionsByQuiz(quizId);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<QuestionDto>> getQuestionsByCourse(@PathVariable String courseId) {
        List<QuestionDto> questions = questionService.getQuestionsByCourse(courseId);
        return ResponseEntity.ok(questions);
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<QuestionDto> updateQuestion(
            @PathVariable String questionId,
            @Valid @RequestBody QuestionDto questionDTO) {
        QuestionDto updatedQuestion = questionService.updateQuestion(questionId, questionDTO);
        return ResponseEntity.ok(updatedQuestion);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable String questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.ok().build();
    }
}
