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
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class SubTaskAddTest {
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
        subTask = new SubTask("test subtask name",new BigDecimal(100000),"test subtask description",new Task("test task name","test task description"));
    }

    @AfterEach
    void tearDown() {
        subTaskRepository.deleteAll();
    }

    @Test
    void add_ShouldAddSubTask_WhenValid() throws SubTaskOperationException {
        when(validationUtil.isValid(subTask)).thenReturn(true);

        subTaskService.add(subTask);

        verify(subTaskRepository, times(1)).save(subTask);
        verify(validationUtil, never()).displayViolations(subTask);
    }

    @Test
    void add_ShouldNotAddSubTask_WhenInvalid() throws SubTaskOperationException {
        when(validationUtil.isValid(subTask)).thenReturn(false);

        subTaskService.add(subTask);

        verify(subTaskRepository, never()).save(subTask);
        verify(validationUtil, times(1)).displayViolations(subTask);
    }

    @Test
    void add_ShouldThrowSubTaskOperationException_WhenExceptionOccurs() {
        when(validationUtil.isValid(subTask)).thenReturn(true);
        doThrow(new RuntimeException()).when(subTaskRepository).save(subTask);

        assertThrows(SubTaskOperationException.class, () -> subTaskService.add(subTask));
    }

}
