package com.trustestate.backend.property.models;

import com.trustestate.backend.common.VerificationStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "units")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Column(nullable = false)
    private String unitNumber; // "A4" or "Floor 2, Unit 5"

    // The independent legal title for THIS specific apartment
    @Column(unique = true, nullable = false)
    private String sectionalTitleNo; // Nairobi/Block 100/1/Unit 4

    @Column(nullable = false)
    private double rentAmount;



    // Simulates the Ardhisasa API check. If FLAGGED, tenants are warned.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;

    private boolean isAvailable = true;
}