package com.trustestate.backend.profile.dto;

import com.trustestate.backend.user_management.dto.UserDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LandlordDto {
    private String nationalId;
    private String kraPin;
    private String companyName;
}
