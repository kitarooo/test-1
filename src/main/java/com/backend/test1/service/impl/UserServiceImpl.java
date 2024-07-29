package com.backend.test1.service.impl;


import com.backend.test1.dto.request.LoginRequest;
import com.backend.test1.dto.request.RegistrationRequest;
import com.backend.test1.dto.response.AuthenticationResponse;
import com.backend.test1.entity.User;
import com.backend.test1.entity.enums.Role;
import com.backend.test1.exception.IncorrectDataException;
import com.backend.test1.exception.IncorrectPasswordsException;
import com.backend.test1.exception.NotFoundException;
import com.backend.test1.exception.UserAlreadyExistException;
import com.backend.test1.repository.UserRepository;
import com.backend.test1.security.jwt.JwtService;
import com.backend.test1.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    JwtService jwtService;

    @Override
    public AuthenticationResponse login(LoginRequest request) {
        User user = userRepository.findUserByUsername(request.getUsername())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден!"));

        if (passwordEncoder.matches(request.getPassword(), user.getPassword()) && user.getUsername().equals(request.getUsername())) {
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .build();
        } else {
            throw new IncorrectDataException("Данные введены неправильно!");
        }
    }

    @Override
    public String registration(RegistrationRequest request) {
        if (userRepository.findUserByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistException("Пользователь с username: " + request.getUsername() + " уже существует!");
        }

        User user = User.builder()
                .username(request.getUsername())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .password(passwordEncoder.encode(request.getPassword()))
                .confirmPassword(request.getConfirmPassword())
                .role(Role.ROLE_USER)
                .build();

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IncorrectPasswordsException("Пароли не совпадают!");
        } else {
            userRepository.save(user);
            return "Регистрация прошла успешно!";
        }
    }
}
