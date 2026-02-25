package com.trustestate.backend.user_management.repository;

import com.trustestate.backend.exception.UserException;
import com.trustestate.backend.user_management.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    Optional<User> findByUserId(String userId) throws UserException;

}
