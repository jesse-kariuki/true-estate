package com.trustestate.backend.payment.service;

import com.trustestate.backend.alert.service.AlertService;
import com.trustestate.backend.common.AlertPriority;
import com.trustestate.backend.common.PaymentStatus;
import com.trustestate.backend.payment.models.Payment;
import com.trustestate.backend.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PenaltyTask {
    private final PaymentRepository paymentRepository;
    private final AlertService alertService;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void applyLateFees() {
        List<Payment> overduePayments = paymentRepository.findAllByStatus(PaymentStatus.OVERDUE);

        for (Payment p : overduePayments) {
            double dailyPenalty = p.getLease().getAgreedRent() * 0.01;
            p.setAmount(p.getAmount() + dailyPenalty);
            paymentRepository.save(p);

            alertService.createAlert(
                    p.getLease().getTenant().getUser(),
                    "Late Payment Penalty",
                    "A daily penalty has been applied to your rent for Lease #" + p.getLease().getId(),
                    AlertPriority.HIGH
            );
        }


    }
}
