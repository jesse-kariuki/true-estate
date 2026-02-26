package com.trustestate.backend.payment.controller;

import com.trustestate.backend.payment.models.Payment;
import com.trustestate.backend.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/rent/{leaseId}")
    @PreAuthorize("hasAuthority('ROLE_TENANT')")
    public ResponseEntity<?> payRent(
            @PathVariable Long leaseId,
            @RequestParam double amount,
            @RequestParam String transactionRef) {
        try {
            Payment payment = paymentService.processRentPayment(leaseId, amount, transactionRef);
            return ResponseEntity.ok(payment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}