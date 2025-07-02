package com.avinashpatil.app.DishaComputerEducationHaladi.controller;

import com.avinashpatil.app.DishaComputerEducationHaladi.dtos.BatchDto;
import com.avinashpatil.app.DishaComputerEducationHaladi.dtos.CourseDto;
import com.avinashpatil.app.DishaComputerEducationHaladi.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto courseDTO) {
        CourseDto courseDto = courseService.createCourse(courseDTO);
        return ResponseEntity.ok(courseDto);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDto> getCourse(@PathVariable String courseId) {
        return ResponseEntity.ok(courseService.getCourse(courseId));
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<CourseDto> updateCourse(
            @PathVariable String courseId,
            @RequestBody CourseDto courseDTO) {
        return ResponseEntity.ok(courseService.updateCourse(courseId, courseDTO));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{courseId}/batches")
    public ResponseEntity<CourseDto> addBatchToCourse(
            @PathVariable String courseId,
            @RequestBody BatchDto batchDTO) {
        return ResponseEntity.ok(courseService.addBatchToCourse(courseId, batchDTO));
    }
}
