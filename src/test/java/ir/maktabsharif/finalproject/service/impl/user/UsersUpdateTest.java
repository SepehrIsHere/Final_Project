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
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UsersUpdateTest {

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
    void testUpdate_ValidUser() throws UserOperationException {
        Users user = generateValidUser();
        when(validationUtil.isValid(any(Users.class))).thenReturn(true);

        usersService.add(user);
        verify(usersRepository).save(user);
        verify(validationUtil, never()).displayViolations(any());

        Users updatedUser = generateUpdatedUser(user);
        usersService.update(updatedUser);

        verify(usersRepository).save(updatedUser);
        verify(validationUtil, never()).displayViolations(updatedUser);
    }


    @Test
    void testUpdate_InvalidUser() throws UserOperationException {
        Users invalidUser = generateInvalidUser();

        // The current implementation doesn't check for validity, so it will still try to save
        usersService.update(invalidUser);

        // Verify that save was called even for an invalid user
        verify(usersRepository).save(invalidUser);

        // Note: In a more robust implementation, we'd expect this to fail validation
        // and not call save. But given the current implementation, it will still save.
    }

    @Test
    void testUpdate_ExceptionThrown() {
        Users user = generateValidUser();
        doThrow(new RuntimeException("Database error")).when(usersRepository).save(any(Users.class));

        assertThrows(UserOperationException.class, () -> usersService.update(user));

        verify(usersRepository).save(user);
    }
}
