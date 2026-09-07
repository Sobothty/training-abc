package com.example.trainingfullstack.repository;

import com.example.trainingfullstack.entity.Task;
import com.example.trainingfullstack.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByUser_UuidOrderByCreatedAtDesc(String userUuid);

    List<Task> findAllByUser_UuidAndTaskStatusOrderByCreatedAtDesc(
            String userUuid,
            TaskStatus taskStatus
    );

    Optional<Task> findByUuidAndUser_Uuid(String uuid, String userUuid);
}
