package com.sersun.trello_app.repository;

import com.sersun.trello_app.DTO.TaskDTO;
import com.sersun.trello_app.model.Task;
import com.sersun.trello_app.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Optional<Task> findByTaskId(Integer taskId);

    List<Task> findByProjectProjectId(Integer projectId);

    List<TaskDTO> findByUserUserId(Integer userId);

    List<Task> findAllByTaskNameContainingIgnoreCaseOrTaskDescriptionContainingIgnoreCase(String name, String description);

    List<Task> findAllByStatus(TaskStatus status);

    List<Task> findAllByDueDateBetween(Date startDate, Date endDate);

}
