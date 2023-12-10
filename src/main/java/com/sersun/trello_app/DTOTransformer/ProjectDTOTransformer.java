package com.sersun.trello_app.DTOTransformer;

import com.sersun.trello_app.DTO.ProjectDTO;
import com.sersun.trello_app.model.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectDTOTransformer {

    public ProjectDTO convertToDto(Project project) {
        return ProjectDTO.builder()
                .projectId(project.getProjectId())
                .projectName(project.getProjectName())
                .projectDescription(project.getProjectDescription())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .build();
    }

    public Project convertToModel(ProjectDTO projectDTO) {
        return Project.builder()
                .projectId(projectDTO.getProjectId())
                .projectName(projectDTO.getProjectName())
                .projectDescription(projectDTO.getProjectDescription())
                .startDate(projectDTO.getStartDate())
                .endDate(projectDTO.getEndDate())
                .build();
    }
}
