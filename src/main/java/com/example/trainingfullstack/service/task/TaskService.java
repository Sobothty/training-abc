package com.example.trainingfullstack.service.task;

import com.example.trainingfullstack.dto.task.TaskRequest;
import com.example.trainingfullstack.dto.task.TaskRequestUpdate;
import com.example.trainingfullstack.dto.task.TaskResponse;
import com.example.trainingfullstack.entity.TaskStatus;

import java.util.List;

public interface TaskService {
    TaskResponse createTask(String userUuid, TaskRequest taskRequest);
    List<TaskResponse> getTasks(String userUuid, TaskStatus status);
    TaskResponse getTask(String userUuid, String taskUuid);
    TaskResponse updateTask(String userUuid, String taskUuid, TaskRequestUpdate taskRequestUpdate);
    void deleteTask(String userUuid, String taskUuid);
}
