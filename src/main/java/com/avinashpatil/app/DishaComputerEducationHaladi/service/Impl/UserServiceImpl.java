package com.avinashpatil.app.DishaComputerEducationHaladi.service.Impl;


import com.avinashpatil.app.DishaComputerEducationHaladi.dtos.UserDTO;
import com.avinashpatil.app.DishaComputerEducationHaladi.entity.Batch;
import com.avinashpatil.app.DishaComputerEducationHaladi.entity.Certificate;
import com.avinashpatil.app.DishaComputerEducationHaladi.entity.Course;
import com.avinashpatil.app.DishaComputerEducationHaladi.entity.User;
import com.avinashpatil.app.DishaComputerEducationHaladi.exceptions.ResourceNotFoundException;
import com.avinashpatil.app.DishaComputerEducationHaladi.repositories.CourseRepository;
import com.avinashpatil.app.DishaComputerEducationHaladi.repositories.UserRepository;
import com.avinashpatil.app.DishaComputerEducationHaladi.service.Impl.NotificationService;
import com.avinashpatil.app.DishaComputerEducationHaladi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        // Disha ID is auto-generated via @PrePersist in the entity
        User savedUser = userRepository.save(user);

        notificationService.notifyUser(
                savedUser.getUserId(),
                "Welcome " + savedUser.getFullName() + "! Your Disha ID is " + savedUser.getDishaId()
        );

        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO getUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        return mapToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(String userId, UserDTO userDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        modelMapper.map(userDTO, user);
        User updatedUser = userRepository.save(user);
        return mapToDTO(updatedUser);
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        userRepository.delete(user);
    }

    @Override
    public UserDTO enrollUserInCourse(String userId, String courseId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + courseId));

        List<Course> courses = user.getCourses();
        if (!courses.contains(course)) {
            courses.add(course);
            user.setCourses(courses);
            userRepository.save(user);

            notificationService.notifyUser(
                    userId,
                    "You have been enrolled in " + course.getCourseName()
            );
        }

        return mapToDTO(user);
    }

    private UserDTO mapToDTO(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        if (user.getCourses() != null) {
            userDTO.setCourseIds(user.getCourses().stream()
                    .map(Course::getCourseId)
                    .collect(Collectors.toList()));
        }
        if (user.getBatches() != null) {
            userDTO.setBatchIds(user.getBatches().stream()
                    .map(Batch::getBatchId)
                    .collect(Collectors.toList()));
        }
        if (user.getCertificates() != null) {
            userDTO.setCertificateIds(user.getCertificates().stream()
                    .map(Certificate::getCertificateId)
                    .collect(Collectors.toList()));
        }
        return userDTO;
    }
}
