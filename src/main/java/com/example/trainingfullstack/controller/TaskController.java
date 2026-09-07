package com.example.trainingfullstack.controller;

import com.example.trainingfullstack.config.OpenApiConfig;
import com.example.trainingfullstack.dto.task.TaskRequest;
import com.example.trainingfullstack.dto.task.TaskRequestUpdate;
import com.example.trainingfullstack.dto.task.TaskResponse;
import com.example.trainingfullstack.entity.TaskStatus;
import com.example.trainingfullstack.security.CustomUserDetailResponse;
import com.example.trainingfullstack.service.task.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Tag(
        name = "Task Services",
        description = "Task CRUD APIs for the authenticated user"
)
@SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @AuthenticationPrincipal CustomUserDetailResponse currentUser,
            @RequestBody @Valid TaskRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskService.createTask(currentUser.getUuid(), request));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks(
            @AuthenticationPrincipal CustomUserDetailResponse currentUser,
            @RequestParam(required = false) TaskStatus status
    ) {
        return ResponseEntity.ok(taskService.getTasks(currentUser.getUuid(), status));
    }

    @GetMapping("/{taskUuid}")
    public ResponseEntity<TaskResponse> getTask(
            @AuthenticationPrincipal CustomUserDetailResponse currentUser,
            @PathVariable String taskUuid
    ) {
        return ResponseEntity.ok(taskService.getTask(currentUser.getUuid(), taskUuid));
    }

    @PatchMapping("/{taskUuid}")
    public ResponseEntity<TaskResponse> updateTask(
            @AuthenticationPrincipal CustomUserDetailResponse currentUser,
            @PathVariable String taskUuid,
            @RequestBody @Valid TaskRequestUpdate request
    ) {
        return ResponseEntity.ok(
                taskService.updateTask(currentUser.getUuid(), taskUuid, request)
        );
    }

    @DeleteMapping("/{taskUuid}")
    public ResponseEntity<?> deleteTask(
            @AuthenticationPrincipal CustomUserDetailResponse currentUser,
            @PathVariable String taskUuid
    ) {
        taskService.deleteTask(currentUser.getUuid(), taskUuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("Task is delete successfully");
    }
}
