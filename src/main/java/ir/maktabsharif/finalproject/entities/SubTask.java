package ir.maktabsharif.finalproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sub_tasks")
public class SubTask extends BaseEntity {

    @Column(unique = true, nullable = false)
    @NotBlank(message = "description name cant be blank ! ")
    @Size(min = 1, max = 70, message = "name cant be smaller than 1 and bigger than 70")
    private String name;

    @Column(unique = true, nullable = false)
    @NotNull(message = "base price cant be null")
    @Min(value = 0, message = "base price cannot be smaller than 0")
    private Double basePrice;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "description cant be empty")
    @Size(min = 1, max = 1000, message = "size cant be smaller than 1 and larger than 1000")
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @NotNull(message = "subtask must belong to a task")
    private Task task;

    @OneToMany
    private List<Order> orders;

    @OneToMany
    private List<Specialist> specialists;

    public SubTask(String name, Double basePrice, String description, Task task) {
        this.name = name;
        this.basePrice = basePrice;
        this.description = description;
        this.task = task;
    }
}
