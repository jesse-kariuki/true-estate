package com.trustestate.backend.alert.controller;

import com.trustestate.backend.alert.models.Alert;
import com.trustestate.backend.alert.repository.AlertRepository;
import com.trustestate.backend.alert.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;
    private final AlertRepository alertRepository;
    @GetMapping("/unread")
    public ResponseEntity<List<Alert>> getUnreadAlerts() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(alertRepository.findByRecipientEmailAndReadFalseOrderByCreatedAtDesc(email));
    }

    @PatchMapping("/read/{alertId}")
    public ResponseEntity<?> markAsRead(@PathVariable Long alertId) {
        try {
            alertService.markAlertAsRead(alertId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
