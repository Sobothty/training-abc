package com.example.trainingfullstack.dto.user;

import com.example.trainingfullstack.entity.Role;

public record UserResponseRole(
        String username,
        String email,
        Role role
) {
}
