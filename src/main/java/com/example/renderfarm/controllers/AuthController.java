package com.example.renderfarm.controllers;

import com.example.renderfarm.api.request.AuthRequest;
import com.example.renderfarm.api.request.RegisterRequest;
import com.example.renderfarm.api.response.AuthData;
import com.example.renderfarm.api.response.DataResponse;
import com.example.renderfarm.api.response.IdResponse;
import com.example.renderfarm.exception.UserFoundException;
import com.example.renderfarm.service.AuthService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@Tag(name = "Контроллеры регистрации и авторизации")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Регистрация")
    @PostMapping("/register")
    public ResponseEntity<IdResponse> registration(@RequestBody RegisterRequest registerRequest) throws UserFoundException {
        return new ResponseEntity<>(authService.registration(registerRequest), HttpStatus.OK);
    }

    @Operation(summary = "Вход через логин/пароль")
    @PostMapping("/login")
    public ResponseEntity<DataResponse<AuthData>> login(@RequestBody AuthRequest authRequest) throws UserFoundException {
        return new ResponseEntity<>(authService.login(authRequest), HttpStatus.OK);
    }

    @Operation(summary = "Выход", security = @SecurityRequirement(name = "default"))
    @GetMapping("/logout")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<DataResponse<?>> getLogout() {
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>(new DataResponse<>().setTimestamp(Instant.now().toEpochMilli()), HttpStatus.OK);
    }
}
