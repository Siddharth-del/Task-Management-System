package com.example.Task.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    
    private String id;
    private String email;
    // private String password;
    private String firstName;
    private String lastName;
    private String createdAt;
    private String updatedAt;
}
