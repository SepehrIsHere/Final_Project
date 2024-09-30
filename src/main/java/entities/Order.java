package entities;

import enumerations.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Column
    @Size(min = 1, max = 50, message = "Invalid suggested price ! ")
    @NotNull(message = "Suggested price cant be null")
    private BigDecimal suggestedPrice;

    @Temporal(TemporalType.DATE)
    @NotNull(message = "date of service cant be null")
    private LocalDate dateOfService;

    @Column
    @NotBlank(message = "description cant be blank!")
    @Size(min = 25, max = 1000, message = "description cant be smaller than 25 and larger than 1000 !")
    private String description;

    @Enumerated
    @NotNull(message = "order status cant be null")
    private OrderStatus status;

    @OneToOne
    private SubTask subTask;

    @OneToOne
    private Customer customer;

    @ManyToOne
    private Specialist specialist;

    @OneToMany
    private List<Suggestions> suggestions;
}
