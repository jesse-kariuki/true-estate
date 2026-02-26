package com.trustestate.backend.lease.models;

import com.trustestate.backend.common.LeaseStatus;
import com.trustestate.backend.profile.models.Tenant;
import com.trustestate.backend.property.models.Unit;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "leases")
public class Lease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    private LocalDate startDate;
    private LocalDate endDate;

    @Column(nullable = false)
    private double agreedRent;

    @Enumerated(EnumType.STRING)
    private LeaseStatus status = LeaseStatus.PENDING;

    private LocalDateTime createdAt = LocalDateTime.now();
}
