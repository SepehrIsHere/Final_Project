package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "suggestions")
public class Suggestions extends BaseEntity {

    @ManyToOne
    private Specialist specialist;

    @ManyToOne
    private Order order;

    @Column
    @NotNull(message = "suggested price cant be null")
    @Size(min = 1, max = 50, message = "suggested price cant be smaller than 1 and larger than 50")
    private BigDecimal suggestedPrice;

    @Column
    @NotNull(message = "suggested date cant be null")
    private LocalDate suggestedDate;

    @Column
    @NotNull(message = "time of work cant be null")
    private LocalTime workTime;

}
