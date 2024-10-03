package ir.maktabsharif.finalproject.repository;

import ir.maktabsharif.finalproject.entities.Users;
import ir.maktabsharif.finalproject.enumerations.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    private Users testUser;

    @BeforeEach
    void setUp() {
        testUser = Users.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .username("johndoe")
                .password("password123")
                .role(Role.NORMAL_USER)
                .build();

        usersRepository.save(testUser);
    }

    @Test
    void testFindUserById() {
        Users foundUser = usersRepository.findUserById(testUser.getId());

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isEqualTo(testUser.getId());
        assertThat(foundUser.getFirstName()).isEqualTo("John");
        assertThat(foundUser.getLastName()).isEqualTo("Doe");
    }

    @Test
    void testFindByFirstNameAndLastName() {
        Users foundUser = usersRepository.findByFirstNameAndLastName("John", "Doe");

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getFirstName()).isEqualTo("John");
        assertThat(foundUser.getLastName()).isEqualTo("Doe");
    }

    @Test
    void testLoginUsers() {
        Users foundUser = usersRepository.loginUsers("johndoe", "password123");

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("johndoe");
        assertThat(foundUser.getPassword()).isEqualTo("password123");
    }
}
