package com.example.trainingfullstack.service.user;

import com.example.trainingfullstack.dto.auth.RegisterRequest;
import com.example.trainingfullstack.dto.user.UserFullResponse;
import com.example.trainingfullstack.dto.user.UserRequestUpdate;
import com.example.trainingfullstack.dto.user.UserResponse;
import com.example.trainingfullstack.entity.User;
import com.example.trainingfullstack.exception.AppException;
import com.example.trainingfullstack.mapper.UserMapper;
import com.example.trainingfullstack.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImplement implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(RegisterRequest registerRequest) {
        if(userRepository.existsByEmail(registerRequest.email())){
            throw new AppException(
                    HttpStatus.BAD_REQUEST,
                    "User's email is already exit"
            );
        }

        if(userRepository.existsByUsername(registerRequest.username())){
            throw new AppException(
                    HttpStatus.BAD_REQUEST,
                    "User's username is already exit"
            );
        }

        User user = userMapper.toEntity(registerRequest);
        user.setPassword(
                passwordEncoder.encode(registerRequest.password()));
        userRepository.save(user);
    }

    @Override
    public List<UserFullResponse> getAllUser() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toFullResponse)
                .toList();
    }

    @Override
    public UserResponse getUserById(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));

        return userMapper.toResponse(user);
    }

    @Override
    public void deleteUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));

        userRepository.delete(user);
    }

    @Override
    public UserResponse updateUserByUuid(String uuid, UserRequestUpdate userRequestUpdate) {
        User user = userRepository.findByUuid(uuid).orElseThrow(
                () -> new AppException(
                        HttpStatus.NOT_FOUND,
                        "User is not found"
                )
        );

        if (userRequestUpdate.email() != null
                && userRepository.existsByEmailAndUuidNot(userRequestUpdate.email(), uuid)) {
            throw new AppException(
                    HttpStatus.BAD_REQUEST,
                    "User's email already exists"
            );
        }

        if (userRequestUpdate.username() != null
                && userRepository.existsByUsernameAndUuidNot(userRequestUpdate.username(), uuid)) {
            throw new AppException(
                    HttpStatus.BAD_REQUEST,
                    "User's username already exists"
            );
        }

        userMapper.updateEntity(userRequestUpdate, user);

        User updatedUser = userRepository.save(user);
        return userMapper.toResponse(updatedUser);
    }
}
