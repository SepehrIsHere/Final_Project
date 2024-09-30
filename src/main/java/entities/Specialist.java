package entities;

import enumerations.SpecialistStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "specialists")
public class Specialist extends Users {

    @Column
    private byte[] personalImage;

    @Enumerated(EnumType.STRING)
    SpecialistStatus specialistStatus;

    @Column
    @NotNull
    @Min(value = 0, message = "score cant be lower than 0")
    @Max(value = 5, message = "score cant be larger than 5")
    private Double score;

    @OneToMany
    private List<SubTask> subTasks;
    @OneToMany
    private List<Order> orders;

    @OneToMany
    private List<Comment> comments;
}
