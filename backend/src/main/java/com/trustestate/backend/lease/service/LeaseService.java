package com.trustestate.backend.lease.service;

import com.trustestate.backend.common.LeaseStatus;
import com.trustestate.backend.lease.LeaseRepository;
import com.trustestate.backend.lease.models.Lease;
import com.trustestate.backend.profile.models.Tenant;
import com.trustestate.backend.profile.repository.TenantRepository;
import com.trustestate.backend.property.models.Unit;
import com.trustestate.backend.property.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class LeaseService {

    private final LeaseRepository leaseRepository;
    private final TenantRepository tenantRepository;
    private final UnitRepository unitRepository;

    @Transactional
    public Lease applyForUnit(Long unitId, LocalDate startDate) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Tenant tenant = tenantRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Tenant profile not found"));

        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new RuntimeException("Unit not found"));

        if (!unit.isAvailable()) {
            throw new RuntimeException("This unit is already occupied");
        }


        Lease lease = new Lease();
        lease.setTenant(tenant);
        lease.setUnit(unit);
        lease.setAgreedRent(unit.getRentAmount());
        lease.setStartDate(startDate);
        lease.setStatus(LeaseStatus.PENDING);

        return leaseRepository.save(lease);
    }

    @Transactional
    public Lease approveLease(Long leaseId) {
        Lease lease = leaseRepository.findById(leaseId)
                .orElseThrow(() -> new RuntimeException("Lease not found"));

        lease.setStatus(LeaseStatus.ACTIVE);
        lease.getUnit().setAvailable(false);

        return leaseRepository.save(lease);
    }
}
