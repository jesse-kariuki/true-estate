package com.trustestate.backend.payment.models;


import com.trustestate.backend.common.PaymentStatus;
import com.trustestate.backend.lease.models.Lease;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lease_id", nullable = false)
    private Lease lease;

    private double amount;
    private LocalDate paymentDate;
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private Double penalty;

    private String transactionReference;
}
