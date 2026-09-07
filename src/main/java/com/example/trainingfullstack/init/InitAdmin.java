package com.example.trainingfullstack.init;

import com.example.trainingfullstack.entity.Role;
import com.example.trainingfullstack.entity.User;
import com.example.trainingfullstack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitAdmin implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("admin")
    private String username;

    @Value("admin@gamil.com")
    private String email;

    @Value("Admin@123")
    private String password;

    @Override
    public void run(String... args) {
        if (userRepository.existsByUsername(username)) {
            log.info("Admin initialization skipped: username '{}' already exists", username);
            return;
        }

        if (userRepository.existsByEmail(email)) {
            log.warn("Admin initialization skipped: email '{}' already exists", email);
            return;
        }

        User admin = User.builder()
                .uuid(UUID.randomUUID().toString())
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(Role.ADMIN)
                .build();

        userRepository.save(admin);
        log.info("Admin user '{}' initialized", username);
    }
}
