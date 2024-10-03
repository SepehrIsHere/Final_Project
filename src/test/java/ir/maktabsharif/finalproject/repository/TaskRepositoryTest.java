package ir.maktabsharif.finalproject.repository;

import ir.maktabsharif.finalproject.entities.Task;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;
    private Task testTask;

    @BeforeEach
    void setUp() {
        testTask = new Task("Sample Task", "This is a sample task description.");

        taskRepository.save(testTask);
    }

    @Test
    void testFindByTaskName() {
        Task foundTask = taskRepository.findByTaskName("Sample Task");

        assertThat(foundTask).isNotNull();
        assertThat(foundTask.getTaskName()).isEqualTo("Sample Task");
        assertThat(foundTask.getTaskDescription()).isEqualTo("This is a sample task description.");
    }

    @Test
    void testFindByTaskId() {
        Task foundTask = taskRepository.findByTaskId(testTask.getId());

        assertThat(foundTask).isNotNull();
        assertThat(foundTask.getId()).isEqualTo(testTask.getId());
        assertThat(foundTask.getTaskName()).isEqualTo("Sample Task");
        assertThat(foundTask.getTaskDescription()).isEqualTo("This is a sample task description.");
    }

}
