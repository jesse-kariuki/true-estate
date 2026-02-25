package com.trustestate.backend.user_management.auth;

import com.trustestate.backend.user_management.dto.AuthResponse;
import com.trustestate.backend.user_management.dto.LoginRequest;
import com.trustestate.backend.user_management.dto.RegisterRequest;

public interface AuthService {


    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);

}
