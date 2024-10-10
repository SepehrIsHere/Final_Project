package ir.maktabsharif.finalproject.entities;

import ir.maktabsharif.finalproject.enumerations.SpecialistStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Specialist extends Users {

    @Column
    private byte[] personalImage;

    @Enumerated(EnumType.STRING)
    private SpecialistStatus specialistStatus;

    @Column
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
