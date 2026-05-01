package com.example.Task.controller;

import com.example.Task.Security.JwtUtils;
import com.example.Task.dtos.ApiResponse;
import com.example.Task.dtos.LoginRequest;
import com.example.Task.dtos.LoginResponse;
import com.example.Task.dtos.RegisterRequest;
import com.example.Task.dtos.UserResponse;
import com.example.Task.model.User;
import com.example.Task.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Register & Login APIs")
public class AuthController {

    private final UserService userService;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<ApiResponse<UserResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        UserResponse user = userService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("User registered successfully", user));
    }

    @PostMapping("/login")
    @Operation(summary = "Login and receive JWT token")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @RequestBody LoginRequest request) {

        User user = userService.authenticate(request);
        String token = jwtUtils.generateToken(user.getId(), user.getRole().name());
        LoginResponse loginResponse =
                new LoginResponse(token, userService.mapToResponse(user));

        return ResponseEntity.ok(
                ApiResponse.success("Login successful", loginResponse)
        );
    }
}