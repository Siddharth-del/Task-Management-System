package com.example.Task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Task.model.User;

@Repository

public interface UserRepository extends JpaRepository<User, String> {

    User findByEmail(String email);
}