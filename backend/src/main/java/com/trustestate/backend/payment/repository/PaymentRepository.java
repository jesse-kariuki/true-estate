package com.trustestate.backend.payment.repository;

import com.trustestate.backend.common.PaymentStatus;
import com.trustestate.backend.payment.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT p FROM Payment p WHERE p.lease.tenant.id = :tenantId ORDER BY p.paymentDate DESC")
    List<Payment> findRecentPaymentsByTenant(@Param("tenantId") Long tenantId, Pageable pageable);

    List<Payment> findAllByStatus(PaymentStatus status);

}
