package com.trustestate.backend.lease;

import com.trustestate.backend.lease.models.Lease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeaseRepository extends JpaRepository<Lease, Integer> {

    Optional<Lease> findById(Long leaseId);

}
