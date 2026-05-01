package com.example.Task.dtos;

import com.example.Task.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {
    private Long taskId;
    private String title;
    private String description;
    private TaskStatus status;
    private String userId;
    private String userEmail;
    private String createdAt;
    private String updatedAt;
}