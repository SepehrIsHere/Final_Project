package ir.maktabsharif.finalproject.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Receipt extends BaseEntity {
    @Column
    @NotBlank
    private String customerFirstName;

    @Column
    @NotBlank
    private String customerLastName;

    @Column
    @NotBlank
    private String specialistFirstName;

    @Column
    @NotBlank
    private String specialistLastName;

    @Column
    private LocalDate dateOfService;

    @Column
    private LocalTime timeOfService;

    @Column
    @NotNull
    private Double totalAmount;

    @Column(unique = true)
    @NotBlank
    private String nameOfOrder;
}
