package com.sersun.trello_app.controller;

import com.sersun.trello_app.DTO.TaskDTO;
import com.sersun.trello_app.model.TaskStatus;
import com.sersun.trello_app.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    @Autowired
    TaskService taskService;

    @GetMapping("/api/projects/{projectId}/tasks")
    public ResponseEntity<List<TaskDTO>> returnAllTaskByProjectId(@PathVariable Integer projectId) {
        return ResponseEntity.ok(taskService.returnAllTaskByProjectId(projectId));
    }

    @PostMapping("/api/projects/{projectId}/tasks")
    public ResponseEntity<String> createNewTask(@PathVariable Integer projectId, @RequestBody TaskDTO taskDTO) {
        taskService.createTask(projectId, taskDTO);
        return ResponseEntity.ok("Task " + taskDTO + " has been created in project with id: " + projectId);
    }

    @GetMapping("/api/projects/{projectId}/tasks/{taskId}")
    public ResponseEntity<TaskDTO> returnTaskById(@PathVariable Integer taskId, @PathVariable Integer projectId) {
        return ResponseEntity.ok(taskService.returnTaskByIdAndByProjectId(taskId, projectId));
    }


    @PutMapping("/api/projects/{projectId}/tasks/{taskId}")
    public ResponseEntity<String> updateTask(@RequestBody TaskDTO taskDTO, @PathVariable Integer taskId, @PathVariable Integer projectId) {
        taskService.updateTask(taskDTO, taskId, projectId);
        return ResponseEntity.ok("Task with id " + taskId + " has been updated!");
    }

    @DeleteMapping("/api/projects/{projectId}/tasks/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Integer taskId, @PathVariable Integer projectId) {
        taskService.deleteTask(taskId, projectId);
        return ResponseEntity.ok("Task with id: " + taskId + " has been deleted!");
    }


    @GetMapping("/api/tasks/assigned/{userId}")
    public ResponseEntity<List<TaskDTO>> returnTasksAssignedToUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(taskService.getTasksByUserId(userId));
    }

    @PutMapping("/api/tasks/{taskId}/assign/{userId}")
    public ResponseEntity<String> assignTaskToUser(@PathVariable Integer taskId, @PathVariable Integer userId) {
        taskService.assignTaskToUser(taskId, userId);
        return ResponseEntity.ok("Task: " + taskId + " has been assigned to user: " + userId + '!');
    }


    @PutMapping("/api/tasks/{taskId}/complete")
    public ResponseEntity<String> completeTask(@PathVariable Integer taskId) {
        taskService.completeTask(taskId);
        return ResponseEntity.ok("Task with taskId: " + taskId + " has been completed");
    }

    // search and filter get controllers for tasks
    @GetMapping("/api/tasks/search")
    public ResponseEntity<List<TaskDTO>> findByNameOrDescription(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "name", required = false) String description) {
        List<TaskDTO> matchedTasks = taskService.findByNameOrDescription(name, description);
        return ResponseEntity.ok(matchedTasks);
    }

    @GetMapping("/api/tasks/filter")
    public ResponseEntity<List<TaskDTO>> filterByStatus(@RequestParam TaskStatus taskStatus) {
        return ResponseEntity.ok(taskService.findAllByTaskStatus(taskStatus));
    }


}
