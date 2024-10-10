package ir.maktabsharif.finalproject.service.impl.task;

import ir.maktabsharif.finalproject.entities.Task;
import ir.maktabsharif.finalproject.exception.TaskOperationException;
import ir.maktabsharif.finalproject.exception.UserOperationException;
import ir.maktabsharif.finalproject.repository.TaskRepository;
import ir.maktabsharif.finalproject.service.impl.TaskServiceImpl;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class AddTaskTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ValidationUtil validationUtil;

    @InjectMocks
    private TaskServiceImpl taskService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        taskService = new TaskServiceImpl(taskRepository,validationUtil);
    }

    @AfterEach
    void tearDown() throws TaskOperationException {
        taskService.deleteAll();
    }

    @Test
    void testAdd_validTask() throws TaskOperationException {
        Task task = new Task("test task", "test task description");
        when(validationUtil.isValid(task)).thenReturn(true);
        taskService.add(task);
        verify(taskRepository).save(task);
        verify(validationUtil, never()).displayViolations(any(Task.class));
    }

    @Test
    void testAdd_invalidTask() throws TaskOperationException {
        Task task = new Task("test task name", null);
        when(validationUtil.isValid(task)).thenReturn(false);

        taskService.add(task);

        verify(taskRepository, never()).save(task);
        verify(validationUtil).displayViolations(task);
    }

    @Test
    void testAdd_ExceptionThrown() throws TaskOperationException {
        Task task = new Task("test task name", null);
        when(validationUtil.isValid(task)).thenReturn(true);
        doThrow(new RuntimeException("Database error")).when(taskRepository).save(task);

        assertThrows(TaskOperationException.class, () -> taskService.add(task));
    }
}
