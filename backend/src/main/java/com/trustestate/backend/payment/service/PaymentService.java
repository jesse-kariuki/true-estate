package com.trustestate.backend.payment.service;

import com.trustestate.backend.common.LeaseStatus;
import com.trustestate.backend.common.PaymentStatus;
import com.trustestate.backend.lease.LeaseRepository;
import com.trustestate.backend.lease.models.Lease;
import com.trustestate.backend.payment.models.Payment;
import com.trustestate.backend.payment.repository.PaymentRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Data
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final LeaseRepository leaseRepository;
    private final ReputationService reputationService;

    @Transactional
    public Payment processRentPayment(Long leaseId, double amount, String ref) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Lease lease = leaseRepository.findById(leaseId)
                .orElseThrow(() -> new RuntimeException("Lease record not found."));


        if (!lease.getTenant().getUser().getEmail().equals(currentUserEmail)) {
            throw new RuntimeException("Unauthorized: You cannot pay rent for a lease that isn't yours.");
        }

        // 4. STATUS CHECK: Is the lease actually active?
        if (lease.getStatus() != LeaseStatus.ACTIVE) {
            throw new RuntimeException("Cannot process payment: This lease is " + lease.getStatus());
        }

        Payment payment = new Payment();
        payment.setLease(lease);
        payment.setAmount(amount);
        payment.setPaymentDate(LocalDate.now());
        payment.setTransactionReference(ref);
        payment.setStatus(PaymentStatus.PAID);

        payment.setDueDate(LocalDate.now().withDayOfMonth(5));

        boolean isOnTime = !payment.getPaymentDate().isAfter(payment.getDueDate());

        Payment savedPayment = paymentRepository.save(payment);

        reputationService.calculateAdvancedScore(lease.getTenant().getId(), savedPayment);

        return savedPayment;
    }
}