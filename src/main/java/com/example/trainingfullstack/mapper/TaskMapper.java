package com.example.trainingfullstack.mapper;


import com.example.trainingfullstack.dto.task.TaskRequest;
import com.example.trainingfullstack.dto.task.TaskRequestUpdate;
import com.example.trainingfullstack.dto.task.TaskResponse;
import com.example.trainingfullstack.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskResponse toResponse(Task task);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Task toEntity(TaskRequest taskRequest);

    List<TaskResponse> toResponseList(List<Task> tasks);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(
            TaskRequestUpdate taskRequestUpdate,
            @MappingTarget Task task
    );

}
