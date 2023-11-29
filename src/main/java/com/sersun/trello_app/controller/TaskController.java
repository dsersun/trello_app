package com.sersun.trello_app.controller;

import com.sersun.trello_app.model.Task;
import com.sersun.trello_app.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class TaskController {
    @Autowired
    TaskService taskService;

    @GetMapping("/api/projects/{projectId}/tasks")
    public ResponseEntity<List<Task>> returnAllTaskByProjectId(@PathVariable Integer projectId){
        return ResponseEntity.ok(taskService.returnAllTaskByProjectId(projectId));
    }

    @PostMapping("/api/projects/{projectId}/tasks")
    public ResponseEntity<String> createNewTask(@PathVariable Integer projectId, @RequestBody @Valid Task task){
        taskService.createTask(projectId, task);
        return ResponseEntity.ok("Task " + task + " has been created in project with id: " + projectId);
    }

    @GetMapping("/api/projects/{projectId}/tasks/{taskId}")
    public ResponseEntity<Task> returnTaskById(@PathVariable Integer taskId, @PathVariable Integer projectId){
        return ResponseEntity.ok(taskService.returnTaskByIdAndByProjectId(taskId, projectId));
    }


    @PutMapping("/api/projects/{projectId}/tasks/{taskId}")
    public ResponseEntity<String> updateTask(@RequestBody @Valid Task task, @PathVariable Integer taskId, @PathVariable Integer projectId){
        taskService.updateTask(task, taskId, projectId);
        return ResponseEntity.ok("Task with id " + taskId + " has been updated!");
    }

    @DeleteMapping("/api/projects/{projectId}/tasks/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Integer taskId, @PathVariable Integer projectId){
        taskService.deleteTask(taskId, projectId);
        return ResponseEntity.ok("Task with id: " + taskId + " has been deleted!");
    }
}
