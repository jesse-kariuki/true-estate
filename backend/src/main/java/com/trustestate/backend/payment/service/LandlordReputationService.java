package com.trustestate.backend.payment.service;

import com.trustestate.backend.profile.models.Landlord;
import com.trustestate.backend.profile.repository.LandlordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LandlordReputationService {
    private final LandlordRepository landlordRepository;

    @Transactional
    public void updateScoreFromReliability(Long landlordId, long daysToResolve) {
        Landlord landlord = landlordRepository.findById(landlordId).orElseThrow();
        int currentScore = landlord.getReliabilityScore();
        int delta = (daysToResolve <= 2) ? 2 : (daysToResolve > 7 ? -5 : 0);

        landlord.setReliabilityScore(Math.max(0, Math.min(100, currentScore + delta)));
        landlordRepository.save(landlord);
    }
}
