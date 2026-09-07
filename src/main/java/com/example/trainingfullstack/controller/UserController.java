package com.example.trainingfullstack.controller;


import com.example.trainingfullstack.config.OpenApiConfig;
import com.example.trainingfullstack.dto.auth.RegisterRequest;
import com.example.trainingfullstack.dto.user.UserFullResponse;
import com.example.trainingfullstack.dto.user.UserRequestUpdate;
import com.example.trainingfullstack.dto.user.UserResponse;
import com.example.trainingfullstack.service.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Tag(
        name = "User Services",
        description = "User Services APIs"
)
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody @Valid RegisterRequest registerRequest){
        userService.createUser(registerRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User is created");
    }

    @GetMapping
    @SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
    public ResponseEntity<List<UserFullResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @GetMapping("/{userId}")
    @SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Integer userId
    ) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PatchMapping("/{userUuid}")
    @SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
    public ResponseEntity<UserResponse> updateUserByUuid(
            @PathVariable String userUuid,
            @RequestBody @Valid UserRequestUpdate request
    ) {
        return ResponseEntity.ok(
                userService.updateUserByUuid(userUuid, request)
        );
    }

    @DeleteMapping("/{userId}")
    @SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
    public ResponseEntity<Void> deleteUserById(
            @PathVariable Integer userId
    ) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }
}
