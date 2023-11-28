package com.sersun.trello_app.controller;

import com.sersun.trello_app.model.Project;
import com.sersun.trello_app.service.ProjectsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    ProjectsService projectsService;
    @GetMapping("/api/projects")
    public ResponseEntity<List<Project>> returnAllProjects(){
        return ResponseEntity.ok(projectsService.returnAllProjects());
    }

    @PostMapping("/api/projects")
    public ResponseEntity<String> createNewProject(@RequestBody @Valid Project project){
        projectsService.createProject(project);
        return ResponseEntity.ok("Project " + project + " has been created");
    }

    @GetMapping("/api/projects/{projectID}")
    public ResponseEntity<Project> returnProjectById(@PathVariable Integer projectID){
        return ResponseEntity.ok(projectsService.returnProjectById(projectID));
    }

    @PutMapping("/api/projects/{projectID}")
    public ResponseEntity<String> updateProject(@PathVariable Integer projectID, @RequestBody Project project){
        projectsService.updateProject(projectID, project);
        return ResponseEntity.ok("Project with id " + projectID + " has been updated!");
    }

    @DeleteMapping("/api/projects/{projectID}")
    public ResponseEntity<String> deleteProject(@PathVariable Integer projectID){
        projectsService.deleteProject(projectID);
        return ResponseEntity.ok("Project with id: " + projectID + " has been deleted!");
    }


}
