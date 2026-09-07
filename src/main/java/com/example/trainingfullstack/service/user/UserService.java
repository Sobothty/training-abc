package com.example.trainingfullstack.service.user;

import com.example.trainingfullstack.dto.auth.RegisterRequest;
import com.example.trainingfullstack.dto.user.UserFullResponse;
import com.example.trainingfullstack.dto.user.UserRequestUpdate;
import com.example.trainingfullstack.dto.user.UserResponse;

import java.util.List;

public interface UserService {
    void createUser(RegisterRequest registerRequest);
    List<UserFullResponse> getAllUser();
    UserResponse getUserById(Integer userId);
    void deleteUserById(Integer id);
    UserResponse updateUserByUuid(String uuid, UserRequestUpdate userRequestUpdate);
    UserResponse currentUser(String username);
}
