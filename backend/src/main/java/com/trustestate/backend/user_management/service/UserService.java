package com.trustestate.backend.user_management.service;

import com.trustestate.backend.exception.UserException;
import com.trustestate.backend.user_management.models.User;

import java.util.List;

public interface UserService {

    User getCurrentUser() throws UserException;

    User getUserById(Long id) throws UserException;
    User getUserByEmail(String email) throws UserException;
    User getUserFromJwtToken(String token) throws UserException;

    List<User> getAllUsers();
}
