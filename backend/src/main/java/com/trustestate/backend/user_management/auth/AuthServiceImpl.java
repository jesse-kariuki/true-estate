package com.trustestate.backend.user_management.auth;

import com.trustestate.backend.common.util.IdGeneratorUtil;
import com.trustestate.backend.config.JwtService;
import com.trustestate.backend.exception.UserException;
import com.trustestate.backend.mapper.UserMapper;
import com.trustestate.backend.user_management.dto.AuthResponse;
import com.trustestate.backend.user_management.dto.LoginRequest;
import com.trustestate.backend.user_management.dto.RegisterRequest;
import com.trustestate.backend.user_management.models.User;
import com.trustestate.backend.user_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final CustomUserImplementation customUserImplementation;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(RegisterRequest request) throws UserException {

        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new UserException("Email already in use");
        }



        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setUserId(IdGeneratorUtil.generateSystemUserId());
        newUser.setRole(request.getRole());
        newUser.setPhone(request.getPhone());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setLastLogin(LocalDateTime.now());
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );

        String jwt = jwtService.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Successfully registered");
        authResponse.setUser(UserMapper.toDto(savedUser));

        return authResponse;
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        String email = request.getEmail();
        String password = request.getPassword();

        Authentication authentication = authenticate(email, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Collection<? extends GrantedAuthority> authorities =authentication.getAuthorities();
        String role = authorities.iterator().next().getAuthority();

        String jwt = jwtService.generateToken(authentication);

        User user = userRepository.findByEmail(email);
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Successfully logged in");
        authResponse.setUser(UserMapper.toDto(user));


        return authResponse;
    }

    private Authentication authenticate(String email, String password) throws UserException {

        UserDetails userDetails = customUserImplementation.loadUserByUsername(email);
        if(userDetails == null){
            throw new UserException("User with email " + email + " not found");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){

            throw new UserException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }
}
