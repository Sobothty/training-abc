package com.example.trainingfullstack.security;

import com.example.trainingfullstack.entity.User;
import com.example.trainingfullstack.exception.AppException;
import com.example.trainingfullstack.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CustomUserDetail implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {

        User user = userRepository.findUserByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(
                        "Username not found"
                )
        );
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
