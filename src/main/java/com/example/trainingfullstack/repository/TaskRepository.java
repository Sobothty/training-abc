package com.example.trainingfullstack.repository;

import com.example.trainingfullstack.entity.Task;
import com.example.trainingfullstack.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByUserId(Integer userId);

    Optional<Task> findTaskByUserIdAndUuid(Integer userId, String uuid);

    List<Task> findAllByUserIdAndTaskStatus(Integer userId, TaskStatus taskStatus);

    Optional<Task> findTaskByIdAndUser_Id(Integer id, Integer userId);
}
