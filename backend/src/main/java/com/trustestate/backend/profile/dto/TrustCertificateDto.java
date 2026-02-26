package com.trustestate.backend.profile.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TrustCertificateDto {
    private String fullName;
    private String nationalId;
    private int currentTrustScore;
    private String trustTier;
    private int onTimePaymentStreak;
    private long totalLeasesCompleted;
    private LocalDateTime issuedAt;
    private String verificationStatus;
    private List<String> recentAchievements;
}
