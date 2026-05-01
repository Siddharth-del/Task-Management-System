package com.example.Task.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Task.Security.JwtUtils;
import com.example.Task.dtos.LoginRequest;
import com.example.Task.dtos.LoginResponse;
import com.example.Task.dtos.RegisterRequest;
import com.example.Task.dtos.UserResponse;
import com.example.Task.model.User;
import com.example.Task.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {
private final UserService userService;

@Autowired
private JwtUtils jwtUtils;

  @PostMapping("/register")
  public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest registerRequest){
    return ResponseEntity.ok( userService.register(registerRequest));
  }


   @PostMapping("/login")
        public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
            Authentication authentication=null;
            try {
                  User user=userService.authenticate(loginRequest);

                String token =jwtUtils.generateToken(user.getId(),user.getRole().name());
                
                return ResponseEntity.ok(new LoginResponse(token,userService.mapToResponse(user)));
                
            } catch (AuthenticationException e) {
                e.printStackTrace();
                return   ResponseEntity.status(401).build();
            }

        }

    
}
