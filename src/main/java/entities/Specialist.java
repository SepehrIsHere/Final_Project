package entities;

import enumerations.SpecialistStatus;
import jakarta.persistence.*;
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
    private Byte personalImage;

    @Enumerated(EnumType.STRING)
    SpecialistStatus specialistStatus;

    @Column
    private Double score;

    @OneToMany
    private List<Order> orders = new ArrayList<>();
}
