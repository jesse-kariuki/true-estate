package com.trustestate.backend.user_management.service;

import com.trustestate.backend.config.JwtService;
import com.trustestate.backend.exception.UserException;
import com.trustestate.backend.user_management.models.User;
import com.trustestate.backend.user_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtProvider;
    @Override
    public User getUserFromJwtToken(String token) throws UserException {
        String email = jwtProvider.getEmailFromToken(token);
        User user = userRepository.findByEmail(email);



        if(user == null) {
            throw new UserException("User with email " + email + " not found");
        }
        return user;
    }

    @Override
    public User getCurrentUser() throws UserException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new UserException("User with email " + email + " not found");
        }
        return user;

    }

    @Override
    public User getUserById(Long id) throws UserException {
        return userRepository.findById(id).orElseThrow(
                () -> new UserException("User with id " + id + " not found")
        );
    }

    @Override
    public User getUserByEmail(String email) throws UserException {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new UserException("User with email " + email + " not found");
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
