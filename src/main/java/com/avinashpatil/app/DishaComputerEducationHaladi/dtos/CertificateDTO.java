package com.avinashpatil.app.DishaComputerEducationHaladi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CertificateDTO {
    private String certificateId;

    @NotBlank(message = "Certificate number is required")
    private String certificateNumber;

    @NotNull(message = "Issue date is required")
    private LocalDate issueDate;

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Course ID is required")
    private String courseId;

    @NotBlank(message = "Batch ID is required")
    private String batchId;
}