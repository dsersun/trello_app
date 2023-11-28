package com.sersun.trello_app.service;

import com.sersun.trello_app.model.Project;
import com.sersun.trello_app.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
public class ProjectsService {
    @Autowired
    ProjectRepository projectRepository;

    // Получение списка всех проектов.
    public List<Project> returnAllProjects(){
        log.info("Returning all projects from the database...");
        return (List<Project>) projectRepository.findAll();
    }

    // Создание нового проекта
    public void createProject(Project project) {
        projectRepository.save(project);
        log.info("Created new project: " + project);
    }

    // Получение информации о конкретном проекте
    public Project returnProjectById(Integer id){
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    // Обновление информации о конкретном проекте
    public void updateProject(Integer id, Project project){
        Project foundProject = projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        project.setProjectId(id);
        projectRepository.save(project);
        log.info("Updated project with id=" + id + " : " + project);
    }

    // Удаление конкретного проекта
    public void deleteProject(Integer id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        projectRepository.delete(project);
        log.info("Project with id=" + id + " has been deleted!");
    }
}
