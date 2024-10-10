package ir.maktabsharif.finalproject.service.impl.task;

import ir.maktabsharif.finalproject.entities.Task;
import ir.maktabsharif.finalproject.exception.TaskOperationException;
import ir.maktabsharif.finalproject.repository.TaskRepository;
import ir.maktabsharif.finalproject.service.impl.TaskServiceImpl;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ValidationUtil validationUtil;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        taskService = new TaskServiceImpl(taskRepository,validationUtil );
    }

    @AfterEach
    void tearDown() throws TaskOperationException {
        taskService.deleteAll();
    }
    @Test
    void delete_ShouldDeleteTask_WhenValid() throws TaskOperationException {
        Task task = new Task("test task name","Test task description");
        taskService.delete(task);

        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    void delete_ShouldThrowTaskOperationException_WhenConstraintViolationExceptionIsThrown() {
        Task task = new Task("Test task name","test task description");
        doThrow(new ConstraintViolationException(null)).when(taskRepository).delete(any(Task.class));

        assertThrows(TaskOperationException.class, () -> taskService.delete(task));
    }

    @Test
    void findAll_ShouldReturnAllTasks_WhenNoException() throws TaskOperationException {
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.findAll();

        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void findAll_ShouldThrowTaskOperationException_WhenExceptionIsThrown() {
        doThrow(new RuntimeException()).when(taskRepository).findAll();

        assertThrows(TaskOperationException.class, () -> taskService.findAll());
    }

    @Test
    void findById_ShouldReturnTask_WhenTaskExists() throws TaskOperationException {
        Task task = new Task("Test task name","test task description");
        when(taskRepository.findByTaskId(anyInt())).thenReturn(task);

        Task result = taskService.findById(1);

        assertNotNull(result);
        verify(taskRepository, times(1)).findByTaskId(1);
    }

    @Test
    void findById_ShouldThrowTaskOperationException_WhenExceptionIsThrown() {
        doThrow(new RuntimeException()).when(taskRepository).findByTaskId(anyInt());

        assertThrows(TaskOperationException.class, () -> taskService.findById(1));
    }

    @Test
    void findByName_ShouldReturnTask_WhenTaskExists() throws TaskOperationException {
        Task task = new Task("Test task name","test task description");
        when(taskRepository.findByTaskName(anyString())).thenReturn(task);

        Task result = taskService.findByName("Task Name");

        assertNotNull(result);
        verify(taskRepository, times(1)).findByTaskName("Task Name");
    }

    @Test
    void findByName_ShouldThrowTaskOperationException_WhenExceptionIsThrown() {
        doThrow(new RuntimeException()).when(taskRepository).findByTaskName(anyString());

        assertThrows(TaskOperationException.class, () -> taskService.findByName("Task Name"));
    }

    @Test
    void deleteAll_ShouldDeleteAllTasks() throws TaskOperationException {
        taskService.deleteAll();

        verify(taskRepository, times(1)).deleteAll();
    }
}