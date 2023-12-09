package com.sersun.trello_app.DTOTransformer;

import com.sersun.trello_app.DTO.TaskDTO;
import com.sersun.trello_app.model.Project;
import com.sersun.trello_app.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskDTOTransformer {

    public TaskDTO convertToDto(Task task) {
        return TaskDTO.builder()
                .taskId(task.getTaskId())
                .projectId(task.getProject().getProjectId())
                .taskName(task.getTaskName())
                .taskDescription(task.getTaskDescription())
                .dueDate(task.getDueDate())
                .status(task.getStatus())
                .executorId(
                        //return 0 (zero) for executorId in case task don't have assigned executor
                        (task.getUser()) !=null ? task.getUser().getUserId() : 0)
                .build();
    }

    public Task convertToModel(TaskDTO taskDTO, Project project){
        return Task.builder()
                .project(project)
                .taskName(taskDTO.getTaskName())
                .taskDescription(taskDTO.getTaskDescription())
                .dueDate(taskDTO.getDueDate())
                .status(taskDTO.getStatus())
                .build();
    }
}
