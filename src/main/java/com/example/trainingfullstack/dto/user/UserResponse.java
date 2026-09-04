package com.example.trainingfullstack.dto.user;

import com.example.trainingfullstack.entity.Role;

public record UserResponse(
        String username,
        String email
) {
}
