package com.example.trainingfullstack.controller;


import com.example.trainingfullstack.dto.auth.RegisterRequest;
import com.example.trainingfullstack.service.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
