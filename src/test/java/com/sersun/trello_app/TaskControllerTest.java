package com.sersun.trello_app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sersun.trello_app.DTO.ProjectDTO;
import com.sersun.trello_app.DTO.TaskDTO;
import com.sersun.trello_app.controller.TaskController;
import com.sersun.trello_app.model.TaskStatus;
import com.sersun.trello_app.service.TaskService;
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

import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class TaskControllerTest {

    MockMvc mockMvc;
    ObjectMapper objectMapper;
    TaskDTO taskDTO;
    ProjectDTO projectDTO;
    @InjectMocks
    private TaskController taskController;
    @Mock
    private TaskService taskService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
        objectMapper = new ObjectMapper();
        taskDTO = TaskDTO.builder()
                .taskId(123)
                .taskName("test Name")
                .taskDescription("test Description")
                .dueDate(new Date())
                .status(TaskStatus.IN_PROGRESS)
                .executorId(null)
                .projectId(123)
                .build();

        projectDTO = ProjectDTO.builder()
                .projectId(123)
                .projectName("testProjectName")
                .projectDescription("test Project Description")
                .startDate(new Date(2022, Calendar.JANUARY, 1))
                .endDate(new Date(2025, Calendar.JANUARY, 1))
                .build();
    }

    @Test
    public void testCreateTaskEndpoint() throws Exception {

        String taskJson = objectMapper.writeValueAsString(taskDTO);
        Integer id = 123;
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/tasks" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(taskJson))
                .andExpect(status().isOk()
                );

        verify(taskService, Mockito.times(1)).createTask(123, taskDTO);

    }


    @Test
    public void getAllTasksByProjectId() throws Exception {

        String taskJson = objectMapper.writeValueAsString(taskDTO);
        Integer project_id = 123;

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/projects/" + project_id + "/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(taskJson))
                .andExpect(status().isOk()
                );
        verify(taskService, Mockito.times(1)).returnAllTaskByProjectId(project_id);
    }

    @Test
    public void getTaskByTaskId() throws Exception {

        String taskJson = objectMapper.writeValueAsString(taskDTO);
        Integer task_id = 123;
        Integer project_id = 123;

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/projects/" + project_id + "/tasks/" + task_id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(taskJson))
                .andExpect(status().isOk()
                );

        verify(taskService, Mockito.times(1)).returnTaskByIdAndByProjectId(task_id, project_id);
    }

    @Test
    public void deleteTask() throws Exception {

        String taskJson = objectMapper.writeValueAsString(taskDTO);
        Integer task_id = 123;
        Integer project_id = 123;

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/projects/" + project_id + "/tasks/" + task_id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(taskJson))
                .andExpect(status().isOk()
                );

        verify(taskService, Mockito.times(1)).deleteTask(task_id, project_id);
    }

    @Test
    public void updateTask() throws Exception {

        String taskJson = objectMapper.writeValueAsString(taskDTO);
        Integer task_id = 123;
        Integer project_id = 123;

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/projects/" + project_id + "/tasks/" + task_id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(taskJson))
                .andExpect(status().isOk()
                );

        verify(taskService, Mockito.times(1)).updateTask(taskDTO, task_id, project_id);
    }


    @Test
    public void getTaskByAssignedUser() throws Exception {
        String taskJson = objectMapper.writeValueAsString(taskDTO);
        Integer user_id = 123;

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/tasks/assigned/" + user_id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(taskJson))
                .andExpect(status().isOk()
                );

        verify(taskService, Mockito.times(1)).getTasksByUserId(user_id);
    }

    @Test
    public void assignTaskToUser() throws Exception {

        String taskJson = objectMapper.writeValueAsString(taskDTO);
        Integer task_id = 123;
        Integer user_id = 123;

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/tasks/" + task_id + "/assign/" + user_id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(taskJson))
                .andExpect(status().isOk()
                );

        verify(taskService, Mockito.times(1)).assignTaskToUser(task_id, user_id);
    }

    @Test
    public void completeTask() throws Exception {

        String taskJson = objectMapper.writeValueAsString(taskDTO);
        Integer task_id = 123;

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/tasks/" + task_id + "/complete")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(taskJson))
                .andExpect(status().isOk()
                );

        verify(taskService, Mockito.times(1)).completeTask(task_id);
    }


}
