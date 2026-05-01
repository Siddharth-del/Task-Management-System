package com.example.Task.service;

import com.example.Task.dtos.TaskRequest;
import com.example.Task.dtos.TaskResponse;
import com.example.Task.exception.ResourceNotFoundException;
import com.example.Task.exception.UnauthorizedException;
import com.example.Task.model.Task;
import com.example.Task.model.TaskStatus;
import com.example.Task.model.User;
import com.example.Task.repository.TaskRepository;
import com.example.Task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    // ── CREATE ──────────────────────────────────────────────────────────────────

    public TaskResponse createTask(TaskRequest request, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus() != null ? request.getStatus() : TaskStatus.PENDING);
        task.setUser(user);

        return mapToResponse(taskRepository.save(task));
    }

    // ── READ — User sees only their tasks ───────────────────────────────────────

    public Page<TaskResponse> getMyTasks(String userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return taskRepository.findByUserEmail(user.getEmail(), pageable)
                .map(this::mapToResponse);
    }

    // ── READ — Admin sees all tasks ──────────────────────────────────────────────

    public Page<TaskResponse> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable).map(this::mapToResponse);
    }

    // ── READ — Single task (owner or admin) ─────────────────────────────────────

    public TaskResponse getTaskById(Long taskId, String userId, boolean isAdmin) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

        if (!isAdmin && !task.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("You do not have access to this task");
        }
        return mapToResponse(task);
    }

    // ── UPDATE ──────────────────────────────────────────────────────────────────

    public TaskResponse updateTask(Long taskId, TaskRequest request, String userId, boolean isAdmin) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

        if (!isAdmin && !task.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("You do not have permission to update this task");
        }

        if (request.getTitle() != null) task.setTitle(request.getTitle());
        if (request.getDescription() != null) task.setDescription(request.getDescription());
        if (request.getStatus() != null) task.setStatus(request.getStatus());

        return mapToResponse(taskRepository.save(task));
    }

    // ── DELETE ──────────────────────────────────────────────────────────────────

    public void deleteTask(Long taskId, String userId, boolean isAdmin) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

        if (!isAdmin && !task.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("You do not have permission to delete this task");
        }
        taskRepository.delete(task);
    }

    // ── MAPPER ──────────────────────────────────────────────────────────────────

    public TaskResponse mapToResponse(Task task) {
        TaskResponse res = new TaskResponse();
        res.setTaskId(task.getTaskId());
        res.setTitle(task.getTitle());
        res.setDescription(task.getDescription());
        res.setStatus(task.getStatus());
        res.setUserId(task.getUser().getId());
        res.setUserEmail(task.getUser().getEmail());
        res.setCreatedAt(task.getCreatedAt() != null ? task.getCreatedAt().toString() : null);
        res.setUpdatedAt(task.getUpdatedAt() != null ? task.getUpdatedAt().toString() : null);
        return res;
    }
}