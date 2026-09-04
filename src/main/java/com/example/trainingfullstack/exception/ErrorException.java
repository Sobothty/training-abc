package com.example.trainingfullstack.exception;

import java.time.LocalDateTime;

public record ErrorException(
        int status,
        String message,
        LocalDateTime timestamp
) {
}
