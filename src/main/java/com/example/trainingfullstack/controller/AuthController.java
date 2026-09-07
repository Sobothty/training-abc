package com.example.trainingfullstack.controller;


import com.example.trainingfullstack.dto.auth.LoginRequest;
import com.example.trainingfullstack.dto.auth.LoginResponse;
import com.example.trainingfullstack.exception.AppException;
import com.example.trainingfullstack.security.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
