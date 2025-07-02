package com.avinashpatil.app.DishaComputerEducationHaladi.service;


import com.avinashpatil.app.DishaComputerEducationHaladi.dtos.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO getUser(String userId);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(String userId, UserDTO userDTO);
    void deleteUser(String userId);
    UserDTO enrollUserInCourse(String userId, String courseId);
}
