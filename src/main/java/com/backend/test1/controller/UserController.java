package backend.microservices.testproject.controller;

import backend.microservices.testproject.dto.request.LoginRequest;
import backend.microservices.testproject.dto.request.RegistrationRequest;
import backend.microservices.testproject.dto.response.AuthenticationResponse;
import backend.microservices.testproject.entity.User;
import backend.microservices.testproject.exception.handler.ExceptionResponse;
import backend.microservices.testproject.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping("/register")
    @Operation(summary = "Регистрация для клиента", description = "Ендпоинт для регистрации!",
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "string"),
                            responseCode = "200", description = "Good"),
                    @ApiResponse(
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)),
                            responseCode = "400", description = "User already exist exception!"
                    )
            })
    public String registration(@RequestBody RegistrationRequest request) {
        return userService.registration(request);
    }

    @PostMapping("/login")
    @Operation(summary = "Авторизация для всех пользователей", description = "Ендпоинт для авторизации и выдачи токена!",
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "string"),
                            responseCode = "200", description = "Good"),
                    @ApiResponse(
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)),
                            responseCode = "400", description = "Incorrect Data Exception!"
                    )
            })
    public AuthenticationResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @PatchMapping("/updateImage")
    @Operation(summary = "Добавить аватарку", description = "Ендпоинт для добавления аватарки",
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "string"),
                            responseCode = "200", description = "Good"),
                    @ApiResponse(
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)),
                            responseCode = "404", description = "File's empty!"
                    )
            })
    public String updateProfilePhoto(@RequestParam MultipartFile multipartFile, @AuthenticationPrincipal User user) throws IOException {
        return userService.updateProfilePhoto(multipartFile, user);
    }
}
