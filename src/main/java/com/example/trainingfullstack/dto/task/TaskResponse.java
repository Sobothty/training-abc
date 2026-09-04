package com.example.trainingfullstack.dto.task;

import com.example.trainingfullstack.entity.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponse(
        String uuid,
        String title,
        String description,
        TaskStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
