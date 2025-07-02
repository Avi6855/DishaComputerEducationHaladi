package com.avinashpatil.app.DishaComputerEducationHaladi.service;

import com.avinashpatil.app.DishaComputerEducationHaladi.dtos.BatchDto;
import com.avinashpatil.app.DishaComputerEducationHaladi.dtos.CourseDto;

import java.util.List;

public interface CourseService {
    CourseDto createCourse(CourseDto courseDTO);
    CourseDto getCourse(String courseId);
    List<CourseDto> getAllCourses();
    CourseDto updateCourse(String courseId, CourseDto courseDTO);
    void deleteCourse(String courseId);
    CourseDto addBatchToCourse(String courseId, BatchDto batchDTO);
}