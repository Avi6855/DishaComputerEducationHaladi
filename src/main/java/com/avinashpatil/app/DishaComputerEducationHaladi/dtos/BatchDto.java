package com.avinashpatil.app.DishaComputerEducationHaladi.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class BatchDto {

    private String batchId;

    private String batchNo;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalTime timing;

    private String courseId;

    private List<String> studentIds;
}
