package com.trustestate.backend.user_management.controller;



import com.trustestate.backend.exception.UserException;
import com.trustestate.backend.mapper.UserMapper;
import com.trustestate.backend.user_management.dto.UserDto;
import com.trustestate.backend.user_management.models.User;
import com.trustestate.backend.user_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController
{
    private final UserService userService;


    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(
            @RequestHeader("Authorization") String jwt
    ) throws UserException {
        User user = userService.getUserFromJwtToken(jwt);
        return ResponseEntity.ok(UserMapper.toDto(user));



    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws UserException {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(UserMapper.toDto(user));



    }
}
