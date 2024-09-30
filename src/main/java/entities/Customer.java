package entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
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
@Table(name = "customers")
public class Customer extends Users {
    @Column
    @Size(min = 2, max = 50,message = "Invalid credit size !")
    private BigDecimal credit;

    @OneToMany
    private List<Order> orders = new ArrayList<>();

    @OneToMany
    private List<Comment> comments = new ArrayList<>();

}
