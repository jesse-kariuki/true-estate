package com.trustestate.backend.profile.repository;

import com.trustestate.backend.profile.models.Tenant;
import com.trustestate.backend.user_management.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Tenant findByNationalId(String nationalId);


    boolean existsByUser(User user);
}
