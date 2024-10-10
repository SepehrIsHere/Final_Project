package ir.maktabsharif.finalproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
public class Customer extends Users {

    @Column
    @Min(value = 0,message = "Credit cannot be smaller than 0")
    private double credit;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @OneToMany
    private List<Comment> comments;

    @OneToOne
    private Users user;

}
