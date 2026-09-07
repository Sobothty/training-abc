package com.example.trainingfullstack.dto.auth;

import java.util.List;

public record LoginResponse(
        String accessToken,
        String tokenType,
        Long expiresIn,
        String username
) {
}
