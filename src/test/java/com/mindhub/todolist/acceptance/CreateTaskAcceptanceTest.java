package com.mindhub.todolist.acceptance;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.mindhub.todolist.dtos.TaskDTO;
import com.mindhub.todolist.entities.TaskStatus;
import com.mindhub.todolist.services.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CreateTaskAcceptanceTest {

    @Autowired
    private TaskService taskService;

    @Test
    void createTask_shouldReturnCreatedTask_whenValidInput() {
        // Given
        TaskDTO newTask = new TaskDTO(null, "Test Task", "This is a test task", TaskStatus.PENDING);

        // When
        TaskDTO createdTask = taskService.createTask(newTask);

        // Then
        assertThat(createdTask, notNullValue());
        assertThat(createdTask.getId(), notNullValue());
        assertThat(createdTask.getTitle(), equalTo("Test Task"));
        assertThat(createdTask.getDescription(), equalTo("This is a test task"));
        assertThat(createdTask.getStatus(), equalTo(TaskStatus.PENDING));
    }
}