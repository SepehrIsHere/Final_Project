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
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class SubTaskServiceImplTest {

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
        subTask = new SubTask("test subtask name", 100000.0, "test subtask description", new Task("test task name", "test task description"));
    }

    @AfterEach
    void tearDown() {
        subTaskRepository.deleteAll();
    }

    @Test
    void findAll_ShouldReturnAllSubTasks_WhenValid() throws SubTaskOperationException {
        List<SubTask> subTasks = Arrays.asList(new SubTask(), new SubTask());
        when(subTaskRepository.findAll()).thenReturn(subTasks);

        List<SubTask> result = subTaskService.findAll();

        assertEquals(2, result.size());
        verify(subTaskRepository, times(1)).findAll();
    }

    @Test
    void findAll_ShouldThrowSubTaskOperationException_WhenExceptionOccurs() {
        doThrow(new RuntimeException()).when(subTaskRepository).findAll();

        assertThrows(SubTaskOperationException.class, () -> subTaskService.findAll());
    }

    @Test
    void findById_ShouldReturnSubTask_WhenSubTaskExists() throws SubTaskOperationException {
        when(subTaskRepository.findSubTaskById(anyInt())).thenReturn(subTask);

        SubTask result = subTaskService.findById(1);

        assertNotNull(result);
        verify(subTaskRepository, times(1)).findSubTaskById(1);
    }

    @Test
    void findById_ShouldThrowSubTaskOperationException_WhenExceptionOccurs() {
        doThrow(new RuntimeException()).when(subTaskRepository).findSubTaskById(anyInt());

        assertThrows(SubTaskOperationException.class, () -> subTaskService.findById(1));
    }

    @Test
    void findByName_ShouldReturnSubTask_WhenSubTaskExists() throws SubTaskOperationException {
        when(subTaskRepository.findByName(anyString())).thenReturn(subTask);

        SubTask result = subTaskService.findByName("SubTask Name");

        assertNotNull(result);
        verify(subTaskRepository, times(1)).findByName("SubTask Name");
    }

    @Test
    void findByName_ShouldThrowSubTaskOperationException_WhenExceptionOccurs() {
        doThrow(new RuntimeException()).when(subTaskRepository).findByName(anyString());

        assertThrows(SubTaskOperationException.class, () -> subTaskService.findByName("SubTask Name"));
    }
}
