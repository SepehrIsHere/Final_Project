package entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {
    @Column
    @NotBlank(message = "comment text cant be blank")
    @Size(min = 1, max = 1000, message = "comment text cant be smaller than 1 and bigger than 1000")
    private String text;

    @Column
    @Min(value = 0, message = "rating point cant be smaller than 0")
    @Max(value = 5, message = "rating point cant be larger than 5")
    private double ratingPoint;

    @OneToOne
    private Specialist specialist;

    @ManyToOne
    private Customer customer;
}
