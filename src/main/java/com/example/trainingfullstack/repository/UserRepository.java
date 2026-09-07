package com.example.trainingfullstack.repository;

import com.example.trainingfullstack.dto.user.UserResponse;
import com.example.trainingfullstack.dto.user.UserResponseRole;
import com.example.trainingfullstack.entity.Role;
import com.example.trainingfullstack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByUsername(String username);
    Optional<UserResponse> findUserByEmail(String email);
    List<UserResponseRole> findAllByRole(Role role);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
