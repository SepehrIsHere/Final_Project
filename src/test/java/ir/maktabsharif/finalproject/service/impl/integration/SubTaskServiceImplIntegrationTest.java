package ir.maktabsharif.finalproject.service.impl.integration;

import ir.maktabsharif.finalproject.entities.SubTask;
import ir.maktabsharif.finalproject.entities.Task;
import ir.maktabsharif.finalproject.exception.SubTaskOperationException;
import ir.maktabsharif.finalproject.repository.SubTaskRepository;
import ir.maktabsharif.finalproject.service.impl.SubTaskServiceImpl;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class SubTaskServiceImplIntegrationTest {

    @Autowired
    private SubTaskServiceImpl subTaskService;

    @Autowired
    private SubTaskRepository subTaskRepository;

    @Autowired
    private ValidationUtil validationUtil;

    private SubTask validSubTask;

    @BeforeEach
    void setUp() {
        Task task = new Task("test task", "test task description");
        validSubTask = new SubTask("test subtask", 1000000.0, "test subtask description", task);
        subTaskService = new SubTaskServiceImpl(validationUtil, subTaskRepository);
    }

    @Test
    void add_ShouldSaveSubTask_WhenValid() throws SubTaskOperationException {
        // Act
        subTaskService.add(validSubTask);

        // Assert
        SubTask savedSubTask = subTaskRepository.findSubTaskById(validSubTask.getId());
        assertNotNull(savedSubTask);
        assertEquals("test subtask", savedSubTask.getName());
    }

    @Test
    void findAll_ShouldReturnAllSubTasks() throws SubTaskOperationException {
        // Arrange
        subTaskService.add(validSubTask);

        // Act
        List<SubTask> subTasks = subTaskService.findAll();

        // Assert
        assertTrue(subTasks.contains(validSubTask));
    }

    @Test
    void findById_ShouldReturnSubTask_WhenExists() throws SubTaskOperationException {
        // Arrange
        subTaskService.add(validSubTask);

        // Act
        SubTask foundSubTask = subTaskService.findById(validSubTask.getId());

        // Assert
        assertNotNull(foundSubTask);
        assertEquals("test subtask", foundSubTask.getName());
    }

    @Test
    void findByName_ShouldReturnSubTask_WhenValidName() throws SubTaskOperationException {
        // Arrange
        subTaskService.add(validSubTask);

        // Act
        SubTask foundSubTask = subTaskService.findByName("test subtask");

        // Assert
        assertNotNull(foundSubTask);
        assertEquals("test subtask", foundSubTask.getName());
    }

    @Test
    void delete_ShouldRemoveSubTask_WhenValid() throws SubTaskOperationException {
        // Arrange
        subTaskService.add(validSubTask);

        // Act
        subTaskService.delete(validSubTask);

        // Assert
        SubTask deletedSubTask = subTaskRepository.findSubTaskById(validSubTask.getId());
        assertNull(deletedSubTask);
    }
}
