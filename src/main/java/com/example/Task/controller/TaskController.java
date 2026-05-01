package com.example.Task.controller;

import com.example.Task.dtos.ApiResponse;
import com.example.Task.dtos.TaskRequest;
import com.example.Task.dtos.TaskResponse;
import com.example.Task.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Tasks", description = "Task CRUD APIs")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @Operation(summary = "Create a new task (authenticated user)")
    public ResponseEntity<ApiResponse<TaskResponse>> createTask(
            @Valid @RequestBody TaskRequest request,
            Principal principal) {

        TaskResponse task = taskService.createTask(request, principal.getName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Task created successfully", task));
    }

    @GetMapping("/my")
    @Operation(summary = "Get current user's tasks (paginated)")
    public ResponseEntity<ApiResponse<Page<TaskResponse>>> getMyTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            Principal principal) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        Page<TaskResponse> tasks = taskService.getMyTasks(principal.getName(), pageable);
        return ResponseEntity.ok(ApiResponse.success("Tasks fetched", tasks));
    }

    @GetMapping("/all")
    @Operation(summary = "Admin only — get all tasks (paginated)")
    public ResponseEntity<ApiResponse<Page<TaskResponse>>> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<TaskResponse> tasks = taskService.getAllTasks(pageable);
        return ResponseEntity.ok(ApiResponse.success("All tasks fetched", tasks));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get task by ID")
    public ResponseEntity<ApiResponse<TaskResponse>> getTask(
            @PathVariable Long id,
            Principal principal,
            @RequestParam(defaultValue = "false") boolean isAdmin) {

        TaskResponse task = taskService.getTaskById(id, principal.getName(), isAdmin);
        return ResponseEntity.ok(ApiResponse.success("Task fetched", task));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update task by ID")
    public ResponseEntity<ApiResponse<TaskResponse>> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request,
            Principal principal,
            @RequestParam(defaultValue = "false") boolean isAdmin) {

        TaskResponse task = taskService.updateTask(id, request, principal.getName(), isAdmin);
        return ResponseEntity.ok(ApiResponse.success("Task updated successfully", task));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete task by ID")
    public ResponseEntity<ApiResponse<Void>> deleteTask(
            @PathVariable Long id,
            Principal principal,
            @RequestParam(defaultValue = "false") boolean isAdmin) {

        taskService.deleteTask(id, principal.getName(), isAdmin);
        return ResponseEntity.ok(ApiResponse.success("Task deleted successfully", null));
    }
}