package com.trustestate.backend.profile.controller;

import com.trustestate.backend.profile.dto.LandlordDto;
import com.trustestate.backend.profile.dto.TenantDto;
import com.trustestate.backend.profile.dto.TrustCertificateDto;
import com.trustestate.backend.profile.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/tenant/{systemUserId}")
    public ResponseEntity<?> onboardTenant(
            @PathVariable String systemUserId,
            @Valid @RequestBody TenantDto request) {
        try {
            var tenant = profileService.createTenantProfile(systemUserId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(tenant);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/landlord/{systemUserId}")
    public ResponseEntity<?> onboardLandlord(
            @PathVariable String systemUserId,
            @Valid @RequestBody LandlordDto request) {
        try {
            var landlord = profileService.createLandlordProfile(systemUserId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(landlord);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/trust-certificate")
    @PreAuthorize("hasAuthority('ROLE_TENANT')")
    public ResponseEntity<TrustCertificateDto> getMyCertificate() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(profileService.generateCertificate(email));
    }

}
