package com.trustestate.backend.property.repository;

import com.trustestate.backend.property.models.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    Optional<Unit> findBySectionalTitleNo(String sectionalTitleNo);
    List<Unit> findByPropertyId(Long propertyId);
}