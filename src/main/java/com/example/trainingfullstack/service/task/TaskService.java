package com.example.trainingfullstack.service.task;

import com.example.trainingfullstack.dto.task.TaskRequest;
import com.example.trainingfullstack.dto.task.TaskRequestUpdate;
import com.example.trainingfullstack.dto.task.TaskResponse;

import java.util.List;

public interface TaskService {
    void createTask(TaskRequest taskRequest, Integer id);
    List<TaskResponse> getAllTask();
    List<TaskResponse> getTaskByUserId(Integer id);
    TaskResponse getTaskById(Integer id);
    TaskResponse updateTask(Integer userId, TaskRequestUpdate taskRequestUpdate);
    void deleteTaskById(Integer id, Integer userId);
}
