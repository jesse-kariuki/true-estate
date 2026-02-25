package com.trustestate.backend.property.dto;

import lombok.Data;

@Data
public class UnitRequest {
    private String unitNumber;
    private String sectionalTitleNo;
    private double rentAmount;
}
