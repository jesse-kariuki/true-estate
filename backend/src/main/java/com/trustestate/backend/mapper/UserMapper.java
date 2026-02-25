package com.trustestate.backend.mapper;

import com.trustestate.backend.user_management.dto.UserDto;
import com.trustestate.backend.user_management.models.User;

public class UserMapper {

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .userId(user.getUserId())
                .phone(user.getPhone())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .lastLogin(user.getLastLogin())
                .build();

    }
}
