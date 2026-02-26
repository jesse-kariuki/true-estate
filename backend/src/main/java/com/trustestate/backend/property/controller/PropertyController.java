package com.trustestate.backend.property.controller;

import com.trustestate.backend.property.dto.PropertyRegistrationRequest;
import com.trustestate.backend.property.models.Property;
import com.trustestate.backend.property.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/properties/manage")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping("/add")
    public ResponseEntity<?> addProperty(
            @RequestBody PropertyRegistrationRequest request) {
        try {
            Property saved = propertyService.registerProperty( request);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}