package com.trustestate.backend.profile.repository;

import com.trustestate.backend.profile.models.Tenant;
import com.trustestate.backend.user_management.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Tenant findByNationalId(String nationalId);


    boolean existsByUser(User user);

    Optional<Tenant> findByUserEmail(String email);

    Optional<Tenant> findByUser(User user);
}
