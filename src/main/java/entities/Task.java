package entities;

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

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {
    @Column
    @NotBlank(message = "task's name cant be blank !")
    @Size(min = 1, max = 100 , message = "task name cant be smaller than 1 and larger than 100")
    private String taskName;

    @Column
    @NotBlank(message = "task description cant be null")
    @Size(min = 1, max = 1000, message = "task description cant be smaller than 1 and larger than 1000")
    private String taskDescription;

    @OneToMany
    private List<SubTask> subTasks = new ArrayList<>();
}
