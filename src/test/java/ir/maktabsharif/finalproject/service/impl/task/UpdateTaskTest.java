package ir.maktabsharif.finalproject.service.impl.task;

import ir.maktabsharif.finalproject.entities.Task;
import ir.maktabsharif.finalproject.exception.TaskOperationException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UpdateTaskTest {
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
    void update_ShouldUpdateTask_WhenValid() throws TaskOperationException {
        Task task = new Task("test task name","test task description");
        taskService.update(task);

        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void update_ShouldThrowTaskOperationException_WhenExceptionIsThrown() {
        Task task = new Task("test task name","test task description");
        doThrow(new RuntimeException()).when(taskRepository).save(any(Task.class));

        assertThrows(TaskOperationException.class, () -> taskService.update(task));
    }
}
