package com.backend.test1.controller;

import com.backend.test1.dto.request.LoginRequest;
import com.backend.test1.dto.request.RegistrationRequest;
import com.backend.test1.dto.response.AuthenticationResponse;
import com.backend.test1.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping("/register")
    public String registration(@RequestBody RegistrationRequest request) {
        return userService.registration(request);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

}
