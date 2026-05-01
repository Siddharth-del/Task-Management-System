package com.example.Task.dtos;
import com.example.Task.model.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
     @NotBlank(message = "Email is required")
     @Email(message = "Invalid email")
    private String email;
     @NotBlank(message = "Password is required")
    private String password;
    private String firstName;
    private String lastName;
    private UserRole role;
}
