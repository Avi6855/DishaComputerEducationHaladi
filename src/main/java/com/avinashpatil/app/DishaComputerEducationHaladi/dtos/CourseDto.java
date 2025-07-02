package com.avinashpatil.app.DishaComputerEducationHaladi.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class CourseDto {

    private String courseId;

    @NotBlank(message = "Course name is required")
    @Size(min = 2, max = 100, message = "Course name must be between 2 and 100 characters")
    private String courseName;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String courseDescription;

    @NotBlank(message = "Duration is required")
    private String courseDuration;

    @NotBlank(message = "Price is required")
    private String coursePrice;

    private String courseOfferPrice;
    private List<BatchDto> batches;
}
