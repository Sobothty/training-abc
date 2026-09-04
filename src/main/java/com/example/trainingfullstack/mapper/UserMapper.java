package com.example.trainingfullstack.mapper;

import com.example.trainingfullstack.dto.auth.RegisterRequest;
import com.example.trainingfullstack.dto.user.UserRequestUpdate;
import com.example.trainingfullstack.dto.user.UserResponse;
import com.example.trainingfullstack.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponse(User user);

    User toEntity(RegisterRequest registerRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "task", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    void updateEntity(UserRequestUpdate userRequestUpdate
    , @MappingTarget User user
    );
}
