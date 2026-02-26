package com.trustestate.backend.alert.models;

import com.trustestate.backend.common.AlertPriority;
import com.trustestate.backend.user_management.models.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "alerts")
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User recipient;

    @Column(nullable = false)
    private String title; // e.g., "Late Fee Applied"

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message; // e.g., "A 1% penalty of 250 KES has been added..."

    @Column(name = "is_read", nullable = false)
    private boolean read = false;

    @Enumerated(EnumType.STRING)
    private AlertPriority priority = AlertPriority.MEDIUM; // HIGH, MEDIUM, LOW

    private LocalDateTime createdAt = LocalDateTime.now();
}
