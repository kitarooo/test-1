package com.backend.test1.service;

import com.backend.test1.dto.request.LoginRequest;
import com.backend.test1.dto.request.RegistrationRequest;
import com.backend.test1.dto.response.AuthenticationResponse;
import com.backend.test1.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    AuthenticationResponse login(LoginRequest request);
    String registration(RegistrationRequest request);
}
