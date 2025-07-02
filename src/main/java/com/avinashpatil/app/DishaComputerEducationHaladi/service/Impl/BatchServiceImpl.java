package com.avinashpatil.app.DishaComputerEducationHaladi.service.Impl;

import com.avinashpatil.app.DishaComputerEducationHaladi.dtos.BatchDto;
import com.avinashpatil.app.DishaComputerEducationHaladi.entity.Batch;
import com.avinashpatil.app.DishaComputerEducationHaladi.entity.Course;
import com.avinashpatil.app.DishaComputerEducationHaladi.entity.User;
import com.avinashpatil.app.DishaComputerEducationHaladi.exceptions.ResourceNotFoundException;
import com.avinashpatil.app.DishaComputerEducationHaladi.repositories.BatchRepository;
import com.avinashpatil.app.DishaComputerEducationHaladi.repositories.CourseRepository;
import com.avinashpatil.app.DishaComputerEducationHaladi.repositories.UserRepository;
import com.avinashpatil.app.DishaComputerEducationHaladi.service.BatchService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatchServiceImpl implements BatchService {

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;


    @Override
    public BatchDto createBatch(BatchDto batchDTO) {
        Course course = courseRepository.findById(batchDTO.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + batchDTO.getCourseId()));

        Batch batch = modelMapper.map(batchDTO, Batch.class);
        batch.setCourse(course);

        // Assign students to the batch
        if (batchDTO.getStudentIds() != null && !batchDTO.getStudentIds().isEmpty()) {
            List<User> students = userRepository.findAllById(batchDTO.getStudentIds());
            if (students.size() != batchDTO.getStudentIds().size()) {
                throw new ResourceNotFoundException("One or more student IDs not found");
            }
            batch.setStudents(students);
        }

        Batch savedBatch = batchRepository.save(batch);


        if (savedBatch.getStudents() != null) {
            savedBatch.getStudents().forEach(student ->
                    notificationService.notifyUser(
                            student.getUserId(),
                            "You have been added to batch " + savedBatch.getBatchNo() + " for course " + course.getCourseName()
                    )
            );
        }

        BatchDto savedBatchDTO = modelMapper.map(savedBatch, BatchDto.class);
        if (savedBatch.getStudents() != null) {
            savedBatchDTO.setStudentIds(savedBatch.getStudents().stream()
                    .map(User::getUserId)
                    .collect(Collectors.toList()));
        }
        return savedBatchDTO;
    }

    @Override
    public BatchDto getBatch(String batchId) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found with ID: " + batchId));
        BatchDto batchDTO = modelMapper.map(batch, BatchDto.class);
        if (batch.getStudents() != null) {
            batchDTO.setStudentIds(batch.getStudents().stream()
                    .map(User::getUserId)
                    .collect(Collectors.toList()));
        }
        return batchDTO;
    }



    @Override
    public List<BatchDto> getBatchesByCourse(String courseId) {
        List<Batch> batches = batchRepository.findByCourseCourseId(courseId);
        return batches.stream()
                .map(batch -> {
                    BatchDto batchDTO = modelMapper.map(batch, BatchDto.class);
                    if (batch.getStudents() != null) {
                        batchDTO.setStudentIds(batch.getStudents().stream()
                                .map(User::getUserId)
                                .collect(Collectors.toList()));
                    }
                    return batchDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public BatchDto updateBatch(String batchId, BatchDto batchDTO) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found with ID: " + batchId));

        modelMapper.map(batchDTO, batch);

        // Update students if provided
        if (batchDTO.getStudentIds() != null) {
            List<User> students = userRepository.findAllById(batchDTO.getStudentIds());
            if (students.size() != batchDTO.getStudentIds().size()) {
                throw new ResourceNotFoundException("One or more student IDs not found");
            }
            batch.setStudents(students);
        }

        Batch updatedBatch = batchRepository.save(batch);

        // Notify updated students
        if (updatedBatch.getStudents() != null) {
            updatedBatch.getStudents().forEach(student ->
                    notificationService.notifyUser(
                            student.getUserId(),
                            "Batch " + updatedBatch.getBatchNo() + " has been updated"
                    )
            );
        }

        BatchDto updatedBatchDTO = modelMapper.map(updatedBatch, BatchDto.class);
        if (updatedBatch.getStudents() != null) {
            updatedBatchDTO.setStudentIds(updatedBatch.getStudents().stream()
                    .map(User::getUserId)
                    .collect(Collectors.toList()));
        }
        return updatedBatchDTO;
    }

    @Override
    public void deleteBatch(String batchId) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found with ID: " + batchId));
        batchRepository.delete(batch);

        // Notify students about deletion
        if (batch.getStudents() != null) {
            batch.getStudents().forEach(student ->
                    notificationService.notifyUser(
                            student.getUserId(),
                            "Batch " + batch.getBatchNo() + " has been deleted"
                    )
            );
        }
    }
}
