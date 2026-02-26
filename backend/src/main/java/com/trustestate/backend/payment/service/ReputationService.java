package com.trustestate.backend.payment.service;

import com.trustestate.backend.common.PaymentStatus;
import com.trustestate.backend.payment.models.Payment;
import com.trustestate.backend.payment.repository.PaymentRepository;
import com.trustestate.backend.profile.models.Tenant;
import com.trustestate.backend.profile.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ReputationService {

    private final TenantRepository tenantRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public void calculateAdvancedScore(Long tenantId, Payment currentPayment) {
        Tenant tenant = tenantRepository.findById(tenantId).orElseThrow();

        double currentScore = tenant.getTrustScore();
        long daysLate = ChronoUnit.DAYS.between(currentPayment.getDueDate(), currentPayment.getPaymentDate());

        double delta = 0;

        if (daysLate <= 0) {
            int streak = countOnTimeStreak(tenantId);
            // Reward increases with the length of the streak (max 2x multiplier)
            double multiplier = Math.min(1.0 + (streak * 0.1), 2.0);
            delta = 5 * multiplier;
        } else {
            // LATE: Exponential penalty
            // 1-3 days: -5 | 4-7 days: -15 | 8+ days: -30
            if (daysLate <= 3) delta = -5;
            else if (daysLate <= 7) delta = -15;
            else delta = -30;
        }

        // Apply a "Dampening Factor" as the score approaches 100
        // It's harder to go from 95 to 100 than 50 to 55
        if (delta > 0 && currentScore > 80) {
            delta *= 0.5;
        }

        double finalScore = Math.max(0, Math.min(100, currentScore + delta));
        tenant.setTrustScore((int) finalScore);
        tenantRepository.save(tenant);
    }

    private int countOnTimeStreak(Long tenantId) {
        List<Payment> history = paymentRepository.findRecentPaymentsByTenant(
                tenantId, PageRequest.of(0, 12));

        int streak = 0;
        for (Payment p : history) {
            boolean isOnTime = !p.getPaymentDate().isAfter(p.getDueDate());

            if (p.getStatus() == PaymentStatus.PAID && isOnTime) {
                streak++;
            } else {
                break;
            }
        }
        return streak;
    }
}