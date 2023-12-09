package com.sersun.trello_app.controller;

import com.sersun.trello_app.DTO.ProjectDTO;
import com.sersun.trello_app.model.Project;
import com.sersun.trello_app.service.ProjectsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    ProjectsService projectsService;
    @GetMapping("/api/projects")
    public ResponseEntity<List<ProjectDTO>> returnAllProjects(){
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

    //search and filter Get controllers
    @GetMapping("/api/projects/search")
    public ResponseEntity<List<Project>> searchProjects(@RequestParam(required = false) String name,
                                                       @RequestParam(required = false) String description) {
        List<Project> projects = projectsService.searchByNameOrDescription(name, description);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/api/projects/filter")
    public ResponseEntity<List<Project>> filterProjects(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<Project> projects = projectsService.filterByStartDateBetween(startDate, endDate);
        return ResponseEntity.ok(projects);
    }


}
