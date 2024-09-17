package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    private BigDecimal suggestedPrice;

    @Column
    private LocalDate suggestedDate;

    @Column
    private LocalTime workTime;

}
