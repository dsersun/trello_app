package com.sersun.trello_app;

import com.sersun.trello_app.DTO.TaskDTO;
import com.sersun.trello_app.controller.TaskController;
import com.sersun.trello_app.model.TaskStatus;
import com.sersun.trello_app.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FilterControllerTest {
    @InjectMocks
    private TaskController taskController;
    @Mock
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFilterByStatus_shouldReturnFilteredTasks() {
        // Arrange
        TaskStatus taskStatus = TaskStatus.DONE;
        List<TaskDTO> filteredTasks = new ArrayList<>();
        when(taskService.findAllByTaskStatus(taskStatus)).thenReturn(filteredTasks);

        ResponseEntity<List<TaskDTO>> response = taskController.filterByStatus(taskStatus);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(filteredTasks, response.getBody());
    }
}