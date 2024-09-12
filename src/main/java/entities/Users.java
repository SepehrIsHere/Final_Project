package entities;

import enumerations.Role;
import jakarta.persistence.*;
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

    @Embedded
    private UserDetails userDetails;

    @Enumerated(EnumType.STRING)
    private Role role;

}
