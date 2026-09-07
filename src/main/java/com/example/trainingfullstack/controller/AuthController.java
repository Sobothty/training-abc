package com.example.trainingfullstack.controller;


import com.example.trainingfullstack.config.OpenApiConfig;
import com.example.trainingfullstack.dto.auth.LoginRequest;
import com.example.trainingfullstack.dto.auth.LoginResponse;
import com.example.trainingfullstack.exception.AppException;
import com.example.trainingfullstack.security.CustomUserDetailResponse;
import com.example.trainingfullstack.security.JwtService;
import com.example.trainingfullstack.service.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(
        name = "Authentication Service",
        description = "Login Service APIs"

)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody @Valid LoginRequest loginRequest
            ) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.username(),
                            loginRequest.password()
                    )
            );

            UserDetails userDetails =
                    (UserDetails) authentication.getPrincipal();

            assert userDetails != null;
            String accessToken = jwtService.generateToken(userDetails);

            return new LoginResponse(
                    accessToken,
                    "Bearer",
                    jwtService.getExpirationSeconds(),
                    userDetails.getUsername()
            );
        } catch (AuthenticationException exception) {
            throw new AppException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid credential"
            );
        }
    }

    @GetMapping("/me")
    @SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
    public ResponseEntity<?> getMe(@AuthenticationPrincipal CustomUserDetailResponse customUserDetailResponse){
        return ResponseEntity.ok(
                userService.currentUser(customUserDetailResponse.getUsername())
        );
    }
}
