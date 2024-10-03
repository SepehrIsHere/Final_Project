package ir.maktabsharif.finalproject.entities;

import ir.maktabsharif.finalproject.enumerations.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Column
    @Min(value =  0,message = "suggested price cant be smaller than 0 ")
    @NotNull(message = "Suggested price cant be null")
    private BigDecimal suggestedPrice;

    @Temporal(TemporalType.DATE)
    @NotNull(message = "date of service cant be null")
    private LocalDate dateOfService;

    @Column
    @NotBlank(message = "description cant be blank!")
    @Size(min = 5, max = 1000, message = "description cant be smaller than 25 and larger than 1000 !")
    private String description;

    @Enumerated
    @NotNull(message = "order status cant be null")
    private OrderStatus status;

    @OneToOne
    private SubTask subTask;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    private Specialist specialist;

    @OneToMany
    private List<Suggestions> suggestions;
}
