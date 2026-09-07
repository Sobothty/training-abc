package com.example.trainingfullstack.service.task;

import com.example.trainingfullstack.dto.task.TaskRequest;
import com.example.trainingfullstack.dto.task.TaskRequestUpdate;
import com.example.trainingfullstack.dto.task.TaskResponse;
import com.example.trainingfullstack.entity.Task;
import com.example.trainingfullstack.entity.TaskStatus;
import com.example.trainingfullstack.entity.User;
import com.example.trainingfullstack.exception.AppException;
import com.example.trainingfullstack.mapper.TaskMapper;
import com.example.trainingfullstack.repository.TaskRepository;
import com.example.trainingfullstack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImplement implements TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public TaskResponse createTask(String userUuid, TaskRequest taskRequest) {
        User user = getUser(userUuid);
        Task task = taskMapper.toEntity(taskRequest);
        task.setUser(user);

        return taskMapper.toResponse(taskRepository.save(task));
    }

    @Override
    public List<TaskResponse> getTasks(String userUuid, TaskStatus status) {
        List<Task> tasks = status == null
                ? taskRepository.findAllByUser_UuidOrderByCreatedAtDesc(userUuid)
                : taskRepository.findAllByUser_UuidAndTaskStatusOrderByCreatedAtDesc(userUuid, status);

        return taskMapper.toResponseList(tasks);
    }

    @Override
    public TaskResponse getTask(String userUuid, String taskUuid) {
        return taskMapper.toResponse(getOwnedTask(userUuid, taskUuid));
    }

    @Override
    @Transactional
    public TaskResponse updateTask(
            String userUuid,
            String taskUuid,
            TaskRequestUpdate taskRequestUpdate
    ) {
        if (taskRequestUpdate.title() == null
                && taskRequestUpdate.description() == null
                && taskRequestUpdate.status() == null) {
            throw new AppException(HttpStatus.BAD_REQUEST, "At least one field is required");
        }

        Task task = getOwnedTask(userUuid, taskUuid);
        taskMapper.updateEntity(taskRequestUpdate, task);

        return taskMapper.toResponse(taskRepository.save(task));
    }

    @Override
    @Transactional
    public void deleteTask(String userUuid, String taskUuid) {
        taskRepository.delete(getOwnedTask(userUuid, taskUuid));
    }

    private User getUser(String userUuid) {
        return userRepository.findByUuid(userUuid).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND, "User is not found")
        );
    }

    private Task getOwnedTask(String userUuid, String taskUuid) {
        return taskRepository.findByUuidAndUser_Uuid(taskUuid, userUuid).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND, "Task is not found")
        );
    }
}
