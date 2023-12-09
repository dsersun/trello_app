package com.sersun.trello_app;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sersun.trello_app.controller.ProjectController;
import com.sersun.trello_app.model.Project;
import com.sersun.trello_app.service.ProjectsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ProjectControllerTest {
    @InjectMocks
    private ProjectController projectController;
    @Mock
    private ProjectsService projectsService;
    MockMvc mockMvc;
    ObjectMapper objectMapper;
    //ProjectDTO projectDTO;
    Project project;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date startDate;
    Date endDate;
    {
        try {
            startDate = dateFormat.parse("2022-01-01");
            startDate = dateFormat.parse("2023-01-01");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }



    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
        objectMapper = new ObjectMapper();
        project = Project.builder()
                .projectId(123)
                .projectName("testProjectName")
                .projectDescription("test Project Description")
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

    @Test
    public void testReturnAllProjects() throws Exception {

        String taskJson = objectMapper.writeValueAsString(project);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/projects")
                .contentType("application/json")
                .content(taskJson))
                .andExpect(status().isOk()
                );

        verify(projectsService, Mockito.times(1)).returnAllProjects();
    }

    @Test
    public void testCreateProjectEndpoint() throws Exception {
        String ProjectJson = objectMapper.writeValueAsString(project);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ProjectJson))
                .andExpect(status().isOk()
                );

        verify(projectsService, Mockito.times(1)).createProject(project);

    }

    @Test
    public void testUpdateProjectEndpoint() throws Exception {

        String ProjectJson = objectMapper.writeValueAsString(project);
        final Integer id = 123;

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/projects" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ProjectJson))
                .andExpect(status().isOk()
                );

        verify(projectsService, Mockito.times(1)).updateProject(id, project);
    }



}
