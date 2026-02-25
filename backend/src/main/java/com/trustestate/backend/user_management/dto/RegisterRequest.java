package com.trustestate.backend.user_management.dto;

import com.trustestate.backend.user_management.domain.UserRole;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private UserRole role;
}

