package com.trustestate.backend.property.dto;

import lombok.Data;

import java.util.List;

@Data
public class PropertyRegistrationRequest {
    private String name;
    private String lrNumber;
    private String location;
    private boolean isSectionalConverted;
    private List<UnitRequest> units;
}