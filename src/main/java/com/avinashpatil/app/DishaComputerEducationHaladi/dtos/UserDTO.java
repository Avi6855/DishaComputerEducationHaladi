package com.avinashpatil.app.DishaComputerEducationHaladi.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private String userId;
    private String dishaId;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Full name is required")
    private String fullName;

    private String activeSessionToken;
    private String lastLoginTime;
    private List<String> courseIds;
    private List<String> batchIds;
    private List<String> certificateIds;
}