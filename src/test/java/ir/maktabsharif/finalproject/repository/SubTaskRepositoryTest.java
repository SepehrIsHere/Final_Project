package ir.maktabsharif.finalproject.repository;

import ir.maktabsharif.finalproject.entities.SubTask;
import ir.maktabsharif.finalproject.entities.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class SubTaskRepositoryTest {

    @Autowired
    private SubTaskRepository subTaskRepository;

    @Autowired
    private TaskRepository taskRepository;

    private Task task;
    private SubTask subTask;

    @BeforeEach
    void setUp() {
        task = new Task("Task 1", "Task description 1");
        subTask = new SubTask("SubTask 1", 100.0, "SubTask description", task);

        taskRepository.save(task);
        subTaskRepository.save(subTask);
    }

    @Test
    void testFindByName() {
        SubTask foundSubTask = subTaskRepository.findByName("SubTask 1");

        assertThat(foundSubTask).isNotNull();
        assertThat(foundSubTask.getName()).isEqualTo("SubTask 1");
        assertThat(foundSubTask.getDescription()).isEqualTo("SubTask description");
    }

    @Test
    void testFindTasksSubTasks() {
        List<SubTask> subTasks = subTaskRepository.findTasksSubTasks(task);

        assertThat(subTasks).isNotEmpty();
        assertThat(subTasks.size()).isEqualTo(1);
        assertThat(subTasks.get(0).getName()).isEqualTo("SubTask 1");
    }

    @Test
    void testFindSubTaskById() {
        SubTask foundSubTask = subTaskRepository.findSubTaskById(subTask.getId());

        assertThat(foundSubTask).isNotNull();
        assertThat(foundSubTask.getId()).isEqualTo(subTask.getId());
    }

    @Test
    void testFindSubTaskById_NotFound() {
        SubTask foundSubTask = subTaskRepository.findSubTaskById(999);

        assertThat(foundSubTask).isNull();
    }
}
