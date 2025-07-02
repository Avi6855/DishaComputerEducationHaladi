package com.avinashpatil.app.DishaComputerEducationHaladi.service.Impl;

import com.avinashpatil.app.DishaComputerEducationHaladi.dtos.*;
import com.avinashpatil.app.DishaComputerEducationHaladi.entity.*;
import com.avinashpatil.app.DishaComputerEducationHaladi.exceptions.ResourceNotFoundException;
import com.avinashpatil.app.DishaComputerEducationHaladi.repositories.*;
import com.avinashpatil.app.DishaComputerEducationHaladi.service.QuizService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public QuizDto createQuiz(QuizDto quizDTO) {
        Batch batch = batchRepository.findById(quizDTO.getBatchId())
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found with ID: " + quizDTO.getBatchId()));

        Quiz quiz = modelMapper.map(quizDTO, Quiz.class);
        quiz.setBatch(batch);
        Quiz savedQuiz = quizRepository.save(quiz);

        // Notify batch students
        batch.getStudents().forEach(student ->
                notificationService.notifyUser(
                        student.getUserId(),
                        "New quiz '" + quiz.getTitle() + "' scheduled for batch " + batch.getBatchNo()
                )
        );

        return modelMapper.map(savedQuiz, QuizDto.class);
    }

    @Override
    public QuizDto getQuiz(String quizId) {
        return null;
    }

    @Override
    public List<QuizDto> getQuizzesByBatch(String batchId) {
        return null;
    }
}
