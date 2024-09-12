package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sub_tasks")
public class SubTask extends BaseEntity {
    @Column
    private BigDecimal basePrice;

    @Column
    private String description;

    @ManyToOne
    private Task task;

    @OneToMany
    private List<Order> orders = new ArrayList<>();
}
