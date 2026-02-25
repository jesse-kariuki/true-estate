package com.trustestate.backend.profile.dto;

import com.trustestate.backend.user_management.dto.UserDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TenantDto {

    @NotBlank(message = "National ID is required for verification")
    private String nationalId;
    @NotBlank(message = "KRA PIN is required")
    private String kraPin;
    private String emergencyContactPhone;
    private String currentEmployer;
}
