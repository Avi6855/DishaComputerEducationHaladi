package com.avinashpatil.app.DishaComputerEducationHaladi.service.Impl;

import com.avinashpatil.app.DishaComputerEducationHaladi.dtos.BatchDto;
import com.avinashpatil.app.DishaComputerEducationHaladi.dtos.CourseDto;
import com.avinashpatil.app.DishaComputerEducationHaladi.entity.Course;
import com.avinashpatil.app.DishaComputerEducationHaladi.entity.User;
import com.avinashpatil.app.DishaComputerEducationHaladi.repositories.CourseRepository;
import com.avinashpatil.app.DishaComputerEducationHaladi.repositories.UserRepository;
import com.avinashpatil.app.DishaComputerEducationHaladi.service.BatchService;
import com.avinashpatil.app.DishaComputerEducationHaladi.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {


    private ModelMapper modelMapper;

    private BatchService batchService;

    private CourseRepository courseRepository;

    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;

    public CourseServiceImpl(ModelMapper modelMapper,CourseRepository courseRepository,BatchService batchService,NotificationService notificationService) {
        this.modelMapper = modelMapper;
        this.courseRepository=courseRepository;
        this.batchService=batchService;
        this.notificationService=notificationService;
    }

    @Override
    public CourseDto createCourse(CourseDto courseDTO) {
        Course course = modelMapper.map(courseDTO, Course.class);
        Course savedCourse = courseRepository.save(course);
        return modelMapper.map(savedCourse, CourseDto.class);
    }

    @Override
    public CourseDto getCourse(String courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return modelMapper.map(course, CourseDto.class);
    }

    @Override
    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(course -> modelMapper.map(course, CourseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CourseDto updateCourse(String courseId, CourseDto courseDTO) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        modelMapper.map(courseDTO, course);
        Course updatedCourse = courseRepository.save(course);
        return modelMapper.map(updatedCourse, CourseDto.class);
    }

    @Override
    public void deleteCourse(String courseId) {
        courseRepository.deleteById(courseId);
    }

    @Override
    public CourseDto addBatchToCourse(String courseId, BatchDto batchDTO) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        batchDTO.setCourseId(courseId);
        BatchDto savedBatchDTO = batchService.createBatch(batchDTO);

        if (batchDTO.getStudentIds() != null && !batchDTO.getStudentIds().isEmpty()) {
            List<User> batchStudents = userRepository.findAllById(batchDTO.getStudentIds());
            batchStudents.forEach(student ->
                    notificationService.notifyUser(
                            student.getUserId(),
                            "You have been added to batch " + savedBatchDTO.getBatchNo() + " for " + course.getCourseName()
                    )
            );
        }

        return getCourse(courseId);
    }
}
