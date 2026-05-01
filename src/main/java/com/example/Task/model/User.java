package com.example.Task.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


  @Entity
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @Table(name="users")
public class User {
    @Id
   @GeneratedValue(strategy =GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String email;
    private String password;
    private String firstName;
    private String lastName;
     
    @Enumerated(EnumType.STRING)
    private UserRole role=UserRole.USER;
    
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
       
    
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Task> tasks;
   
}
