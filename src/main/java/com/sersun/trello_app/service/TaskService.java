package com.sersun.trello_app.service;

import com.sersun.trello_app.DTO.TaskDTO;
import com.sersun.trello_app.DTOTransformer.TaskDTOTransformer;
import com.sersun.trello_app.model.Project;
import com.sersun.trello_app.model.Task;
import com.sersun.trello_app.model.TaskStatus;
import com.sersun.trello_app.model.User;
import com.sersun.trello_app.repository.ProjectRepository;
import com.sersun.trello_app.repository.TaskRepository;
import com.sersun.trello_app.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    TaskDTOTransformer taskDTOTransformer;


    // Получение списка задач для конкретного проекта
    public List<TaskDTO> returnAllTaskByProjectId(Integer projectId) {
        List<Task> taskListByProject = taskRepository.findAll();
        log.info("Returning all task by projectId from the database...");
        return taskListByProject.stream()
                .map(e -> taskDTOTransformer.convertToDto(e))
                .filter(e -> e.getProjectId() == projectId)
                .collect(Collectors.toList());
    }

    // Создание новой задачи в рамках проекта
    public void createTask(Integer projectId, TaskDTO taskDTO) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        taskDTO.setProjectId(project.getProjectId());
        Task task = taskDTOTransformer.convertToModel(taskDTO, project);
        taskRepository.save(task);
        log.info("Created new task: " + task);
    }

    // Получение информации о конкретной задаче в рамках проекта
    public TaskDTO returnTaskByIdAndByProjectId(Integer taskId, Integer projectId) {
        log.info("Returning all task by projectId from the database...");
        return taskRepository.findByProjectProjectId(projectId).stream()
                .map(e -> taskDTOTransformer.convertToDto(e))
                .filter(task -> task.getTaskId() == taskId)
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    // Обновление информации о конкретной задаче в рамках проекта
    public Task updateTask(TaskDTO updatedTask, Integer taskId, Integer projectId) {
        Task existingTask = taskRepository.findByProjectProjectId(projectId).stream()
                .filter(e -> e.getTaskId().equals(taskId))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        existingTask.setTaskName(updatedTask.getTaskName());
        existingTask.setTaskDescription(updatedTask.getTaskDescription());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setDueDate(updatedTask.getDueDate());

        log.info("Updated task with id=" + taskId + " : " + updatedTask);
        return taskRepository.save(existingTask);
    }

    // Удаление конкретной задачи в рамках проекта
    public void deleteTask(Integer taskId, Integer projectId) {
        Task task = taskRepository.findByProjectProjectId(projectId).stream()
                .filter(e -> e.getTaskId().equals(taskId))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        taskRepository.delete(task);
        log.info("Task with id=" + taskId + " has been deleted!");
    }

    //                  Assignment service
    public List<TaskDTO> getTasksByUserId(Integer userId) {
        log.info("Returned list of tasks assigned to user: " + userId);
        List<Task> taskByUserId = taskRepository.findAll();
        return taskByUserId.stream()
                .map(e -> taskDTOTransformer.convertToDto(e))
                .filter(e -> e.getExecutorId().equals(userId))
                .collect(Collectors.toList());
    }

    // assign task to user
    public void assignTaskToUser(Integer taskId, Integer userId) {
        Task existingTask = taskRepository.findByTaskId(taskId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        User user = usersRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        existingTask.setUser(user);
        taskRepository.save(existingTask);
        log.info("Task: " + taskId + " assignet to user: " + userId);
    }


    public Task completeTask(Integer taskId) {
        Task completedTask = taskRepository.findByTaskId(taskId).stream()
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        completedTask.setStatus(TaskStatus.DONE);
        return taskRepository.save(completedTask);
    }

    public List<TaskDTO> findByNameOrDescription(String name, String description) {
        return taskRepository
                .findAllByTaskNameContainingIgnoreCaseOrTaskDescriptionContainingIgnoreCase(name, description)
                .stream()
                .map(e -> taskDTOTransformer.convertToDto(e))
                .collect(Collectors.toList());
    }

    public List<TaskDTO> findAllByTaskStatus(TaskStatus taskStatus) {
        return taskRepository
                .findAllByStatus(taskStatus)
                .stream()
                .map(e -> taskDTOTransformer.convertToDto(e))
                .collect(Collectors.toList());
    }

    public List<TaskDTO> filterByDueDate(Date startDate, Date endDate) {
        return taskRepository
                .findAllByDueDateBetween(startDate, endDate)
                .stream()
                .map(e -> taskDTOTransformer.convertToDto(e))
                .collect(Collectors.toList());
    }
}
