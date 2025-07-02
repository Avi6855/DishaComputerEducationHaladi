package com.avinashpatil.app.DishaComputerEducationHaladi.service;


import com.avinashpatil.app.DishaComputerEducationHaladi.dtos.BatchDto;

import java.util.List;

public interface BatchService {
    BatchDto createBatch(BatchDto batchDTO);
    BatchDto getBatch(String batchId);
    List<BatchDto> getBatchesByCourse(String courseId);
    BatchDto updateBatch(String batchId, BatchDto batchDTO);
    void deleteBatch(String batchId);
}
