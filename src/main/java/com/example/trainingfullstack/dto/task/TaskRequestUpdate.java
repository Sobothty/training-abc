package com.example.trainingfullstack.dto.task;

import com.example.trainingfullstack.entity.TaskStatus;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TaskRequestUpdate(
        @Size(max = 150, message = "Title cannot exceed 150 characters")
        @Pattern(regexp = ".*\\S.*", message = "Title must not be blank")
        String title,

        @Size(max = 1000, message = "Description cannot exceed 1000 characters")
        String description,

        TaskStatus status
) {
}
