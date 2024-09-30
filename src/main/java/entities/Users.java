package entities;

import enumerations.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users extends BaseEntity {

    @OneToOne
    private UserDetails userDetails;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "role cant be null")
    private Role role;

}
