package ir.maktabsharif.finalproject.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {

    @Column(unique = true, nullable = false)
    @NotBlank(message = "task's name cant be blank !")
    @Size(min = 1, max = 100, message = "task name cant be smaller than 1 and larger than 100")
    private String taskName;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "task description cant be null")
    @Size(min = 1, max = 1000, message = "task description cant be smaller than 1 and larger than 1000")
    private String taskDescription;

    @OneToMany
    private List<SubTask> subTasks;

    public Task(String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }
}
