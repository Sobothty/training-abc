package com.example.trainingfullstack.service.task;

import com.example.trainingfullstack.dto.task.TaskRequest;
import com.example.trainingfullstack.dto.task.TaskRequestUpdate;
import com.example.trainingfullstack.dto.task.TaskResponse;
import com.example.trainingfullstack.entity.Task;
import com.example.trainingfullstack.entity.User;
import com.example.trainingfullstack.exception.AppException;
import com.example.trainingfullstack.mapper.TaskMapper;
import com.example.trainingfullstack.repository.TaskRepository;
import com.example.trainingfullstack.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskServiceImplement implements TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public void createTask(TaskRequest taskRequest, Integer id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND, "User is not found")
        );
        Task task = taskMapper.toEntity(taskRequest);
        task.setUser(user);
        Task tasks = taskRepository.save(task);
    }

    @Override
    public List<TaskResponse> getAllTask() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    @Override
    public List<TaskResponse> getTaskByUserId(Integer id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new AppException(
                        HttpStatus.NOT_FOUND, "User is not found"
                )
        );
        return taskRepository.findAllByUserId(user.getId())
                .stream().map(taskMapper::toResponse).toList();
    }

    @Override
    public TaskResponse getTaskById(Integer id) {

        Task task = taskRepository.findById(id).orElseThrow(
                () -> new AppException(
                        HttpStatus.NOT_FOUND,
                        "Task is not found"
                ));

        return taskMapper.toResponse(task);
    }

    @Override
    public TaskResponse updateTask(Integer userId, TaskRequestUpdate taskRequestUpdate) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new AppException(
                        HttpStatus.NOT_FOUND,
                        "User is not found"
                )
        );

        Task task = taskRepository.findTaskByUserIdAndUuid(user.getId(), taskRequestUpdate.uuid()).orElseThrow(
                () -> new AppException(
                        HttpStatus.NOT_FOUND,
                        "User's task is not found"
                )
        );
        taskMapper.updateEntity(taskRequestUpdate, task);
        Task task1 = taskRepository.save(task);

        return taskMapper.toResponse(task1);
    }

    @Override
    public void deleteTaskById(Integer id, Integer userId) {

        User user = userRepository.findById(id).orElseThrow(
                () -> new AppException(
                        HttpStatus.NOT_FOUND,
                        "User is not found"
                )
        );

        Task task = taskRepository.findTaskByIdAndUser_Id(id, userId).orElseThrow(
                () -> new AppException(
                        HttpStatus.NOT_FOUND,
                        "Task is not found"
                )
        );

        taskRepository.delete(task);
    }
}
