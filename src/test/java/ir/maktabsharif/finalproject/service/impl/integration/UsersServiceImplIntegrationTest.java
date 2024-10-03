package ir.maktabsharif.finalproject.service.impl.integration;

import ir.maktabsharif.finalproject.enumerations.Role;
import ir.maktabsharif.finalproject.entities.Users;
import ir.maktabsharif.finalproject.exception.UserOperationException;
import ir.maktabsharif.finalproject.repository.UsersRepository;
import ir.maktabsharif.finalproject.service.impl.UsersServiceImpl;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class UsersServiceImplIntegrationTest {

    @Autowired
    private UsersServiceImpl usersService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ValidationUtil validationUtil;

    private Users validUser;

    @BeforeEach
    void setUp() {
        validUser = Users.builder()
                .firstName("test firstname")
                .lastName("test lastname")
                .email("test@gmail.com")
                .username("test username")
                .password("testpass123")
                .role(Role.NORMAL_USER)
                .build();
        usersService = new UsersServiceImpl(validationUtil, usersRepository);
    }

    @AfterEach
    void tearDown() {
        usersRepository.deleteAll();
    }

    @Test
    void add_ShouldSaveUser_WhenValid() throws UserOperationException {
        // Act
        usersService.add(validUser);

        // Assert
        Users savedUser = usersRepository.findUserById(validUser.getId());
        assertEquals("test username", savedUser.getUsername());
    }

    @Test
    void findAll_ShouldReturnAllUsers() throws UserOperationException {
        // Arrange
        usersService.add(validUser);

        // Act
        List<Users> users = usersService.findAll();

        // Assert
        assertTrue(users.contains(validUser));
    }

    @Test
    void login_ShouldReturnUser_WhenCredentialsAreValid() throws UserOperationException {
        // Arrange
        usersService.add(validUser);

        // Act
        Users loggedInUser = usersService.login("test username", "testpass123");

        // Assert
        assertEquals("test username", loggedInUser.getUsername());
    }

    @Test
    void findById_ShouldReturnUser_WhenExists() throws UserOperationException {
        // Arrange
        usersService.add(validUser);

        // Act
        Users foundUser = usersService.findById(validUser.getId());

        // Assert
        assertEquals("test username", foundUser.getUsername());
    }
}
