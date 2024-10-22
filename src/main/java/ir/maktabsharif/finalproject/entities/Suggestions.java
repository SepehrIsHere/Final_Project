package ir.maktabsharif.finalproject.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "suggestions")
public class Suggestions extends BaseEntity {

    @ManyToOne
    private Specialist specialist;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Order order;

    @Column
    @NotNull(message = "suggested price cant be null")
    @Min(value = 0, message = "suggest price cannot be smaller than 0")
    private Double suggestedPrice;

    @Column
    @NotNull(message = "suggested date cant be null")
    @Future(message = "the date cannot be earlier than today's date")
    private LocalDate suggestedDate;

    @Column
    @NotNull(message = "time of work cant be null")
    private LocalTime workTime;

}
