package com.trustestate.backend.alert.repository;

import com.trustestate.backend.alert.models.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {

    List<Alert> findByRecipientEmailAndReadFalseOrderByCreatedAtDesc(String email);

    List<Alert> findByRecipientEmailOrderByCreatedAtDesc(String email);
    Optional<Alert> findByIdAndRecipientEmail(Long id, String email);

    long countByRecipientEmailAndReadFalse(String email);
}
