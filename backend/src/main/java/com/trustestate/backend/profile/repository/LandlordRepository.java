package com.trustestate.backend.profile.repository;

import com.trustestate.backend.profile.models.Landlord;
import com.trustestate.backend.user_management.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LandlordRepository extends JpaRepository<Landlord, Long> {

    Landlord findByNationalId(String nationalId);


    Optional<Landlord> findByUserEmail(String email);

    boolean existsByUser(User user);
}
