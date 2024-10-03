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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersAddTest {

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

    Users generateValidUser(){
       return Users.builder().
                firstName("test firstname").
                lastName("test lastname").
                email("test@email.com").
                username("testUsername").
                password("testPassword12").
                role(Role.NORMAL_USER).
                build();
    }

    Users generateInvalidUser(){
       return Users.builder().
                firstName("firstname").
                lastName("lastname").
                build();
    }

    @Test
    void testAdd_ValidUser() throws UserOperationException {
        Users user = generateValidUser();
        when(validationUtil.isValid(user)).thenReturn(true);
        usersService.add(user);
        verify(usersRepository).save(user);
        verify(validationUtil, never()).displayViolations(any(Users.class));
    }

    @Test
    void testAdd_InvalidUser() throws UserOperationException {
        Users user = generateInvalidUser();
        when(validationUtil.isValid(user)).thenReturn(false);

        usersService.add(user);
        verify(usersRepository, never()).save(any());
        verify(validationUtil).displayViolations(user);
    }



    @Test
    void testAdd_ExceptionThrown() {
        Users user = generateInvalidUser();
        when(validationUtil.isValid(user)).thenReturn(true);
        doThrow(new RuntimeException("Database error")).when(usersRepository).save(user);

        assertThrows(UserOperationException.class, () -> usersService.add(user));
    }

}
