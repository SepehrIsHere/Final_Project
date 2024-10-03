package ir.maktabsharif.finalproject.service.impl.user;

import ir.maktabsharif.finalproject.entities.Users;
import ir.maktabsharif.finalproject.enumerations.Role;
import ir.maktabsharif.finalproject.exception.UserOperationException;
import ir.maktabsharif.finalproject.repository.UsersRepository;
import ir.maktabsharif.finalproject.service.impl.UsersServiceImpl;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersServiceImplTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private ValidationUtil validationUtil;

    @InjectMocks
    private UsersServiceImpl usersService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usersService = new UsersServiceImpl(validationUtil, usersRepository);
    }

    @AfterEach
    void tearDown() {
        usersRepository.deleteAll();
    }

    Users generateValidUser() {
        return Users.builder().
                firstName("test firstname").
                lastName("test lastname").
                email("test@email.com").
                username("testUsername").
                password("testPassword12").
                role(Role.NORMAL_USER).
                build();
    }

    Users generateInvalidUser() {
        return Users.builder().
                firstName("firstname").
                lastName("lastname").
                build();
    }

    Users generateUpdatedUser(Users user) {
        return Users.builder().
                firstName(user.getFirstName()).
                lastName(user.getLastName()).
                email(user.getEmail()).
                username(user.getUsername()).
                password(user.getPassword()).
                role(user.getRole()).
                build();
    }
    @Test
    void testFindAll_Success() throws UserOperationException {
        List<Users> expectedUsers = Arrays.asList(
                generateValidUser(),
                generateValidUser()
        );
        when(usersRepository.findAll()).thenReturn(expectedUsers);

        List<Users> actualUsers = usersService.findAll();

        assertEquals(expectedUsers, actualUsers);
        verify(usersRepository).findAll();
    }

    @Test
    void testFindAll_ExceptionThrown() {
        when(usersRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        assertThrows(UserOperationException.class, () -> usersService.findAll());
        verify(usersRepository).findAll();
    }

    @Test
    void testFindById_Success() throws UserOperationException {
        int userId = 1;
        Users expectedUser = generateValidUser();
        when(usersRepository.findUserById(userId)).thenReturn(expectedUser);

        Users actualUser = usersService.findById(userId);

        assertEquals(expectedUser, actualUser);
        verify(usersRepository).findUserById(userId);
    }

    @Test
    void testFindById_ExceptionThrown() {
        int userId = 1;
        when(usersRepository.findUserById(userId)).thenThrow(new RuntimeException("User not found"));

        assertThrows(UserOperationException.class, () -> usersService.findById(userId));
        verify(usersRepository).findUserById(userId);
    }

    @Test
    void testLogin_Success() throws UserOperationException {
        String username = "john@example.com";
        String password = "password123";
        Users expectedUser = generateValidUser();
        when(usersRepository.loginUsers(username, password)).thenReturn(expectedUser);

        Users actualUser = usersService.login(username, password);

        assertEquals(expectedUser, actualUser);
        verify(usersRepository).loginUsers(username, password);
    }

    @Test
    void testLogin_ExceptionThrown() {
        String username = "john@example.com";
        String password = "wrongpassword";
        when(usersRepository.loginUsers(username, password)).thenThrow(new RuntimeException("Invalid credentials"));

        assertThrows(UserOperationException.class, () -> usersService.login(username, password));
        verify(usersRepository).loginUsers(username, password);
    }
}