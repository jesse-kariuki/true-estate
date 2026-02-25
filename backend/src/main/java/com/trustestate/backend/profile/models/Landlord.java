package com.trustestate.backend.profile.models;


import com.trustestate.backend.common.VerificationStatus;
import com.trustestate.backend.user_management.models.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "landlords")
public class Landlord {

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

    private int reliabilityScore = 100;

    private String companyName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;


}
