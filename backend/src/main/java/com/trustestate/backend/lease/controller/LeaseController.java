package com.trustestate.backend.lease.controller;

import com.trustestate.backend.lease.service.LeaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/lease")
@RequiredArgsConstructor
public class LeaseController {

    private final LeaseService leaseService;


    @PostMapping("/apply/{unitId}")
    public ResponseEntity<?> apply(@PathVariable Long unitId, @RequestParam LocalDate startDate) {
        return ResponseEntity.ok(leaseService.applyForUnit(unitId, startDate));
    }

    @PatchMapping("/approve/{leaseId}")
    @PreAuthorize("hasAuthority('ROLE_LANDLORD')")
    public ResponseEntity<?> approve(@PathVariable Long leaseId) {
        return ResponseEntity.ok(leaseService.approveLease(leaseId));
    }

}
