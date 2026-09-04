package com.example.trainingfullstack.controller;


import com.example.trainingfullstack.dto.auth.RegisterRequest;
import com.example.trainingfullstack.service.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Tag(
        name = "Authentication",
        description = "Authentication and user registration APIs"
)
public class UserController {
    private final UserService userService;

    public ResponseEntity<?> createUser(RegisterRequest registerRequest){
        userService.createUser(registerRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User is created");
    }
}
