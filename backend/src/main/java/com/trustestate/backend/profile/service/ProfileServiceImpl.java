package com.trustestate.backend.profile.service;

import com.trustestate.backend.common.VerificationStatus;
import com.trustestate.backend.exception.UserException;
import com.trustestate.backend.profile.dto.LandlordDto;
import com.trustestate.backend.profile.dto.TenantDto;
import com.trustestate.backend.profile.models.Landlord;
import com.trustestate.backend.profile.models.Tenant;
import com.trustestate.backend.profile.repository.LandlordRepository;
import com.trustestate.backend.profile.repository.TenantRepository;
import com.trustestate.backend.user_management.domain.UserRole;
import com.trustestate.backend.user_management.models.User;
import com.trustestate.backend.user_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final LandlordRepository landlordRepository;

    @Override
    @Transactional
    public Tenant createTenantProfile(String userId, TenantDto request) throws UserException {

        User user = userRepository.findByUserId(userId).orElseThrow(() -> new UserException("User not found with userId: " + userId));

        if (user.getRole() != UserRole.ROLE_TENANT) {
            throw new UserException("Invalid role. User is not a Tenant.");
        }
        if (tenantRepository.existsByUser(user)) {
            throw new UserException("Tenant profile already exists for this user.");
        }
        Tenant tenant = new Tenant();
        tenant.setUser(user);
        tenant.setNationalId(request.getNationalId());
        tenant.setKraPin(request.getKraPin());
        tenant.setEmergencyContactPhone(request.getEmergencyContactPhone());

        tenant.setTrustScore(50);
        tenant.setVerificationStatus(VerificationStatus.PENDING);
        tenant.setLastScoreUpdate(LocalDateTime.now());

        return tenantRepository.save(tenant);
    }

    @Override
    @Transactional
    public Landlord createLandlordProfile(String userId, LandlordDto request) throws UserException {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException("User not found"));

        if (user.getRole() != UserRole.ROLE_LANDLORD) {
            throw new UserException("Invalid role. User is not a Landlord.");
        }

        if (landlordRepository.existsByUser(user)) {
            throw new RuntimeException("Landlord profile already exists.");
        }

        Landlord landlord = new Landlord();
        landlord.setUser(user);
        landlord.setNationalId(request.getNationalId());
        landlord.setKraPin(request.getKraPin());
        landlord.setCompanyName(request.getCompanyName());
        landlord.setReliabilityScore(100);
        landlord.setVerificationStatus(VerificationStatus.PENDING);

        return landlordRepository.save(landlord);
    }
}
