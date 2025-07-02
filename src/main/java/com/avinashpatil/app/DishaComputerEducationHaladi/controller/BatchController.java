package com.avinashpatil.app.DishaComputerEducationHaladi.controller;


import com.avinashpatil.app.DishaComputerEducationHaladi.dtos.BatchDto;
import com.avinashpatil.app.DishaComputerEducationHaladi.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/batches")
public class BatchController {

    @Autowired
    private BatchService batchService;

    @PostMapping
    public ResponseEntity<BatchDto> createBatch(@RequestBody BatchDto batchDTO) {
        return ResponseEntity.ok(batchService.createBatch(batchDTO));
    }

    @GetMapping("/{batchId}")
    public ResponseEntity<BatchDto> getBatch(@PathVariable String batchId) {
        return ResponseEntity.ok(batchService.getBatch(batchId));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<BatchDto>> getBatchesByCourse(@PathVariable String courseId) {
        return ResponseEntity.ok(batchService.getBatchesByCourse(courseId));
    }

    @PutMapping("/{batchId}")
    public ResponseEntity<BatchDto> updateBatch(
            @PathVariable String batchId,
            @RequestBody BatchDto batchDTO) {
        return ResponseEntity.ok(batchService.updateBatch(batchId, batchDTO));
    }

    @DeleteMapping("/{batchId}")
    public ResponseEntity<Void> deleteBatch(@PathVariable String batchId) {
        batchService.deleteBatch(batchId);
        return ResponseEntity.ok().build();
    }
}
