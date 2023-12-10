package com.sersun.trello_app;

import com.sersun.trello_app.DTO.TaskDTO;
import com.sersun.trello_app.controller.TaskController;
import com.sersun.trello_app.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class SearchControllersTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByNameOrDescription() {
        // Подготовка тестовых данных
        List<TaskDTO> expectedTasks = Arrays.asList(new TaskDTO());
        String name = "taskName";
        String description = "taskDescription";

        // Настройка поведения мокированного сервиса
        when(taskService.findByNameOrDescription(name, null)).thenReturn(expectedTasks);
        when(taskService.findByNameOrDescription(null, description)).thenReturn(expectedTasks);
        when(taskService.findByNameOrDescription(name, description)).thenReturn(expectedTasks);
        when(taskService.findByNameOrDescription(null, null)).thenReturn(expectedTasks);

        // Тестирование и проверка результатов
        ResponseEntity<List<TaskDTO>> response = taskController.findByNameOrDescription(name, null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedTasks, response.getBody());

        response = taskController.findByNameOrDescription(null, description);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedTasks, response.getBody());

        response = taskController.findByNameOrDescription(name, description);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedTasks, response.getBody());

        response = taskController.findByNameOrDescription(null, null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedTasks, response.getBody());
    }
}
