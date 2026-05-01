package com.example.Task.service;


import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Task.dtos.LoginRequest;
import com.example.Task.dtos.RegisterRequest;
import com.example.Task.dtos.UserResponse;
import com.example.Task.model.User;
import com.example.Task.model.UserRole;
import com.example.Task.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    

     private final UserRepository userRepository;
      
     @Autowired
     private PasswordEncoder passwordEncoder;
     
     public UserResponse register(RegisterRequest request){
        UserRole role=request.getRole()!=null?request.getRole():UserRole.USER;
        User user=User.builder()
                 .email(request.getEmail())
                 .firstName(request.getFirstName())
                 .lastName(request.getLastName())
                 .password(passwordEncoder.encode(request.getPassword()))
                 .role(role)
                 .build();
    
        User savedUser= userRepository.save(user);
         
        return mapToResponse(savedUser);
     }

     public UserResponse mapToResponse(User savedUser){
        UserResponse response=new UserResponse();
        response.setId(savedUser.getId());
        response.setEmail(savedUser.getEmail());
       
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
       return response;
     }

     public User authenticate(LoginRequest loginRequest){
       User user =userRepository.findByEmail(loginRequest.getEmail());
       if(user==null)  throw new RuntimeException("Invalid Credentials");
       if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
          throw new RuntimeException("Invalid Credentials");
       }
       return user;
     }

}
