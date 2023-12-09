package com.sersun.trello_app.DTO;

import com.sersun.trello_app.model.TaskStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TaskDTO {
    @NotEmpty
    private Integer taskId;
    @NotEmpty
    private Integer projectId;
    @NotEmpty
    private String taskName;
    private String taskDescription;
    private Date dueDate;
    private TaskStatus status;
    private Integer executorId;
}
