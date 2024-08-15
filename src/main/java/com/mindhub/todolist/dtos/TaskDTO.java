package com.mindhub.todolist.dtos;

import com.mindhub.todolist.entities.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    @Schema(description = "Unique identifier of the task", example = "1")
    private Long id;

    @Schema(description = "Title of the task", example = "Complete Spring Boot project")
    private String title;

    @Schema(description = "Detailed description of the task", example = "Implement all CRUD operations and document the API")
    private String description;

    @Schema(description = "Current status of the task", example = "PENDING")
    private TaskStatus status;
}
