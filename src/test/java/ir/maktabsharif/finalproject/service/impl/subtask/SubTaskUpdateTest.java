package ir.maktabsharif.finalproject.service.impl.subtask;

import ir.maktabsharif.finalproject.entities.SubTask;
import ir.maktabsharif.finalproject.entities.Task;
import ir.maktabsharif.finalproject.exception.SubTaskOperationException;
import ir.maktabsharif.finalproject.repository.SubTaskRepository;
import ir.maktabsharif.finalproject.service.impl.SubTaskServiceImpl;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubTaskUpdateTest {

    @Mock
    private SubTaskRepository subTaskRepository;

    @Mock
    private ValidationUtil validationUtil;

    @InjectMocks
    private SubTaskServiceImpl subTaskService;

    private SubTask subTask;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        subTaskService = new SubTaskServiceImpl(validationUtil, subTaskRepository);
        subTask = new SubTask("test subtask name", new BigDecimal(100000), "test subtask description", new Task("test task name", "test task description"));
    }

    @AfterEach
    void tearDown() {
        subTaskRepository.deleteAll();
    }

    @Test
    void update_ShouldUpdateSubTask_WhenValid() throws SubTaskOperationException {
        subTaskService.update(subTask);

        verify(subTaskRepository, times(1)).save(subTask);
    }

    @Test
    void update_ShouldThrowSubTaskOperationException_WhenExceptionOccurs() {
        doThrow(new RuntimeException()).when(subTaskRepository).save(subTask);

        assertThrows(SubTaskOperationException.class, () -> subTaskService.update(subTask));
    }

}
