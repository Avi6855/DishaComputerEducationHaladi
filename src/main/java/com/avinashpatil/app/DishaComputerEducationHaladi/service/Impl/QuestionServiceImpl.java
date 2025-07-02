package com.avinashpatil.app.DishaComputerEducationHaladi.service.Impl;

import com.avinashpatil.app.DishaComputerEducationHaladi.dtos.QuestionDto;
import com.avinashpatil.app.DishaComputerEducationHaladi.entity.Course;
import com.avinashpatil.app.DishaComputerEducationHaladi.entity.Question;
import com.avinashpatil.app.DishaComputerEducationHaladi.entity.Quiz;
import com.avinashpatil.app.DishaComputerEducationHaladi.exceptions.BadRequestException;
import com.avinashpatil.app.DishaComputerEducationHaladi.exceptions.ResourceNotFoundException;
import com.avinashpatil.app.DishaComputerEducationHaladi.repositories.CourseRepository;
import com.avinashpatil.app.DishaComputerEducationHaladi.repositories.QuestionRepository;
import com.avinashpatil.app.DishaComputerEducationHaladi.repositories.QuizRepository;
import com.avinashpatil.app.DishaComputerEducationHaladi.service.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CourseRepository courseRepository;



    @Override
    public QuestionDto createQuestion(QuestionDto questionDTO) {
        Course course = courseRepository.findById(questionDTO.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + questionDTO.getCourseId()));

        Question question = modelMapper.map(questionDTO, Question.class);
        question.setCourse(course);
        // Quiz is optional at creation; it can be added later via addQuestionToQuiz
        Question savedQuestion = questionRepository.save(question);

        return modelMapper.map(savedQuestion, QuestionDto.class);
    }

    @Override
    public QuestionDto getQuestion(String questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with ID: " + questionId));
        return modelMapper.map(question, QuestionDto.class);
    }

    @Override
    public List<QuestionDto> getQuestionsByCourse(String courseId) {
        return questionRepository.findByCourseCourseId(courseId).stream()
                .map(question -> modelMapper.map(question, QuestionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionDto> getQuestionsByQuiz(String quizId) {
        return questionRepository.findByQuizQuizId(quizId).stream()
                .map(question -> modelMapper.map(question, QuestionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addQuestionToQuiz(String quizId, String questionId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with ID: " + quizId));
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with ID: " + questionId));

        // Validate that the question's course matches the quiz's course (via batch)
        String quizCourseId = quiz.getBatch().getCourse().getCourseId();
        if (!question.getCourse().getCourseId().equals(quizCourseId)) {
            throw new BadRequestException("Question with ID " + questionId + " does not belong to the same course as quiz with ID " + quizId);
        }

        question.setQuiz(quiz);
        questionRepository.save(question);
    }

    @Override
    public QuestionDto updateQuestion(String questionId, QuestionDto questionDTO) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with ID: " + questionId));

        Course course = courseRepository.findById(questionDTO.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + questionDTO.getCourseId()));

        modelMapper.map(questionDTO, question);
        question.setCourse(course); // Update course if changed
        Question updatedQuestion = questionRepository.save(question);
        return modelMapper.map(updatedQuestion, QuestionDto.class);
    }

    @Override
    public void deleteQuestion(String questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with ID: " + questionId));
        questionRepository.delete(question);
    }
}
