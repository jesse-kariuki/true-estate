package com.trustestate.backend.profile.models;

import com.trustestate.backend.common.VerificationStatus;
import com.trustestate.backend.user_management.models.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tenants")
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(unique = true)
    private String nationalId;

    @Column(unique = true)
    private String kraPin;

    private int trustScore = 50;

    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;

    private String emergencyContactPhone;

    private LocalDateTime lastScoreUpdate;
}
