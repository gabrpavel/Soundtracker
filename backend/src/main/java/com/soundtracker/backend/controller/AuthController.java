package com.soundtracker.backend.controller;

import com.soundtracker.backend.dto.request.SignInRequest;
import com.soundtracker.backend.dto.request.SignUpRequest;
import com.soundtracker.backend.dto.response.JwtAuthenticationResponse;
import com.soundtracker.backend.service.user.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {

    private final AuthenticationService authenticationService;

    /**
     * Регистрация пользователя
     *
     * @param request объект запроса на регистрацию
     * @return объект ответа с токеном
     */
    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    /**
     * Авторизация пользователя
     *
     * @param request объект запроса на авторизацию
     * @return объект ответа с токеном
     */
    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }
}