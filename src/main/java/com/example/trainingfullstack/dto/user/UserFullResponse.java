package com.example.trainingfullstack.dto.user;

public record UserFullResponse(
        String id,
        String uuid,
        String username,
        String email
) {
}
