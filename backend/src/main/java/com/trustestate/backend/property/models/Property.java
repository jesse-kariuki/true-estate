package com.trustestate.backend.property.models;

import com.trustestate.backend.profile.models.Landlord;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The Landlord who registered this property
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landlord_id", nullable = false)
    private Landlord landlord;

    @Column(nullable = false, unique = true)
    private String lrNumber; // Land Registration No (e.g., Nairobi/Block 100/1)

    @Column(nullable = false)
    private String name; // e.g., "Sunset Apartments"

    @Column(nullable = false)
    private String location; // e.g., "Kilimani, Nairobi"

    // The core hackathon flex: Is this legally compliant?
    @Column(nullable = false)
    private boolean isSectionalConverted = false;

    // One Mother Title has many Sectional Units
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Unit> units;

    private LocalDateTime createdAt = LocalDateTime.now();
}