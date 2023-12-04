package com.sersun.trello_app.repository;

import com.sersun.trello_app.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Optional<Task> findByTaskId(Integer taskId);

    List<Task> findByProjectProjectId(Integer projectId);

    List<Task> findByUserUserId(Integer userId);

}
