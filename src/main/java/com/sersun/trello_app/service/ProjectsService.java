package com.sersun.trello_app.service;

import com.sersun.trello_app.DTO.ProjectDTO;
import com.sersun.trello_app.DTOTransformer.ProjectDTOTransformer;
import com.sersun.trello_app.model.Project;
import com.sersun.trello_app.repository.ProjectRepository;
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
public class ProjectsService {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProjectDTOTransformer projectDTOTransformer;


    public List<ProjectDTO> returnAllProjects(){
        log.info("Returning all projects from the database...");
        List<Project> allProjectList = projectRepository.findAll();
        return allProjectList.stream()
                .map(e -> projectDTOTransformer.convertToDto(e))
                .collect(Collectors.toList());
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
    public ResponseStatusException updateProject(Integer id, Project project) {
        if (id == null) {
            return new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            Project foundProject = projectRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
            project.setProjectId(id);
            projectRepository.save(project);
            log.info("Updated project with id=" + id + " : " + project);
            return null;
        }
    }

    // Удаление конкретного проекта
    public void deleteProject(Integer id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        projectRepository.delete(project);
        log.info("Project with id=" + id + " has been deleted!");
    }

    // find and filters

    public List<Project> searchByNameOrDescription(String name, String description){
        return projectRepository.findAllByProjectNameContainingIgnoreCaseOrProjectDescriptionContainingIgnoreCase(name, description);
    }

    public List<Project> filterByStartDateBetween(Date startDate, Date endDate){
        return projectRepository.findAllByStartDateBetween(startDate, endDate);
    }
}
