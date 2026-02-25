package com.trustestate.backend.user_management.dto;

import com.trustestate.backend.user_management.domain.UserRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserDto {

    private String firstName;
    private String lastName;
    private String userId;
    private String email;
    private String phone;
    private UserRole role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;
}
