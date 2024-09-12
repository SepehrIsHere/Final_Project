package entities;

import enumerations.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity{
    @Column
    private BigDecimal suggestedPrice;

    @Temporal(TemporalType.DATE)
    private LocalDate dateOfService;

    @Column
    private String description;

    @Enumerated
    private OrderStatus status;

    @OneToOne
    private SubTask subTask;

    @OneToOne
    private Customer customer;

    @ManyToOne
    private Specialist specialist;
}
