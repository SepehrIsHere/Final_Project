package ir.maktabsharif.finalproject.service.impl.integration;

import ir.maktabsharif.finalproject.entities.Task;
import ir.maktabsharif.finalproject.exception.TaskOperationException;
import ir.maktabsharif.finalproject.repository.TaskRepository;
import ir.maktabsharif.finalproject.service.impl.TaskServiceImpl;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class TaskServiceImplIntegrationTest {

    @Autowired
    private TaskServiceImpl taskService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ValidationUtil validationUtil;

    private Task validTask;

    @BeforeEach
    void setUp() {
        validTask =new Task("test task name","test task description");
        taskService = new TaskServiceImpl(validationUtil, taskRepository);
    }

    @Test
    void add_ShouldSaveTask_WhenValid() throws TaskOperationException {
        // Act
        taskService.add(validTask);

        // Assert
        Task savedTask = taskRepository.findByTaskId(validTask.getId());
        assertEquals("test task name", savedTask.getTaskName());
    }

    @Test
    void findAll_ShouldReturnAllTasks() throws TaskOperationException {
        // Arrange
        taskService.add(validTask);

        // Act
        List<Task> tasks = taskService.findAll();

        // Assert
        assertTrue(tasks.contains(validTask));
    }

    @Test
    void findById_ShouldReturnTask_WhenExists() throws TaskOperationException {
        // Arrange
        taskService.add(validTask);

        // Act
        Task foundTask = taskService.findById(validTask.getId());

        // Assert
        assertEquals("test task name", foundTask.getTaskName());
    }

    @Test
    void findByName_ShouldReturnTask_WhenNameIsValid() throws TaskOperationException {
        // Arrange
        taskService.add(validTask);

        // Act
        Task foundTask = taskService.findByName("test task name");

        // Assert
        assertEquals("test task name", foundTask.getTaskName());
    }

    @Test
    void delete_ShouldRemoveTask_WhenValid() throws TaskOperationException {
        // Arrange
        taskService.add(validTask);

        // Act
        taskService.delete(validTask);

        // Assert
        Task deletedTask = taskRepository.findByTaskId(validTask.getId());
        assertNull(deletedTask);
    }
}
