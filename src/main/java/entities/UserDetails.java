package entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_details")
public class UserDetails extends BaseEntity {
    @NotBlank(message = "first name cant be empty")
    @Size(min = 3, max = 45, message = "Invalid first name")
    private String firstName;

    @NotBlank(message = "last name cant be empty")
    @Size(min = 3, max = 40, message = "Invalid last name")
    private String lastName;

    @NotBlank(message = "email cant be empty")
    @Email(message = "Invalid email format ! please enter a correct email")
    private String email;

    @NotBlank(message = "username cant be blank")
    @Size(min = 2, max = 30, message = "username must be bigger than 2 and smaller than 30 characters")
    private String username;

    @NotBlank(message = "password cant be blank")
    @Size(min = 2, max = 30, message = "password must be bigger than 2 and smaller than 30 characters")
    private String password;

    @OneToOne
    private Users users;
}
