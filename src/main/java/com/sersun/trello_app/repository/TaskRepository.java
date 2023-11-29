package com.sersun.trello_app.repository;

import com.sersun.trello_app.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {
    List<Task> findByProjectProjectId(Integer projectId);
}
