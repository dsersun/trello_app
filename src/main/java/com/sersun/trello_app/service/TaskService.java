package com.sersun.trello_app.service;

import com.sersun.trello_app.model.Project;
import com.sersun.trello_app.model.Task;
import com.sersun.trello_app.repository.ProjectRepository;
import com.sersun.trello_app.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ProjectRepository projectRepository;


    // Получение списка задач для конкретного проекта
    public List<Task> returnAllTaskByProjectId(Integer projectId){
        log.info("Returning all task by projectId from the database...");
        return taskRepository.findByProjectProjectId(projectId);
    }

    // Создание новой задачи в рамках проекта
    public void createTask(Integer projectId, Task task){
        Project project = projectRepository.findById(projectId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        task.setProject(project);
        taskRepository.save(task);
        log.info("Created new task: " + task);
    }

    // Получение информации о конкретной задаче в рамках проекта
    public Task returnTaskByIdAndByProjectId(Integer taskId, Integer projectId){
        log.info("Returning all task by projectId from the database...");
        return taskRepository.findByProjectProjectId(projectId).stream()
                .filter(e -> e.getTaskId().equals(taskId))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    // Обновление информации о конкретной задаче в рамках проекта
    public Task updateTask(Task updatedTask, Integer taskId, Integer projectId){
        Task existingTask = taskRepository.findByProjectProjectId(projectId).stream()
                .filter(e -> e.getTaskId().equals(taskId))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        existingTask.setTaskName(updatedTask.getTaskName());
        existingTask.setTaskDescription(updatedTask.getTaskDescription());
        existingTask.setDueDate(updatedTask.getDueDate());
        existingTask.setStatus(updatedTask.getStatus());
        log.info("Updated task with id=" + taskId + " : " + updatedTask);
        return taskRepository.save(existingTask);
    }

    // Удаление конкретной задачи в рамках проекта
    public void deleteTask(Integer taskId, Integer projectId){
        Task task = taskRepository.findByProjectProjectId(projectId).stream()
                .filter(e -> e.getTaskId().equals(taskId))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        taskRepository.delete(task);
        log.info("Task with id=" + taskId + " has been deleted!");
    }
}
