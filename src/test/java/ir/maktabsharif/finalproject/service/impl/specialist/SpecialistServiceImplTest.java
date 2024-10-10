package ir.maktabsharif.finalproject.service.impl.specialist;

import ir.maktabsharif.finalproject.entities.Specialist;
import ir.maktabsharif.finalproject.enumerations.Role;
import ir.maktabsharif.finalproject.enumerations.SpecialistStatus;
import ir.maktabsharif.finalproject.exception.SpecialistOperationException;
import ir.maktabsharif.finalproject.repository.SpecialistRepository;
import ir.maktabsharif.finalproject.service.impl.SpecialistServiceImpl;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class SpecialistServiceImplTest {

    @Mock
    private SpecialistRepository specialistRepository;

    @Mock
    private ValidationUtil validationUtil;

    @InjectMocks
    private SpecialistServiceImpl specialistService;

    Specialist generateValidSpecialist(){
        return Specialist.
                builder().
                firstName("test firstname").
                lastName("test lastname").
                email("test@gmail.com").
                username("test username").
                password("testpas123").
                role(Role.SPECIALIST).
                specialistStatus(SpecialistStatus.PENDING_APPROVAL)
                .build();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        specialistService = new SpecialistServiceImpl(validationUtil,specialistRepository);
    }

    @AfterEach
    void tearDown(){
        specialistRepository.deleteAll();
    }

    @Test
    void findByFirstNameAndLastName_ShouldReturnSpecialist_WhenSpecialistExists() throws SpecialistOperationException {
        Specialist specialist = generateValidSpecialist();
        when(specialistRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(specialist);

        Specialist result = specialistService.findByFirstNameAndLastName("John", "Doe");

        assertNotNull(result);
        verify(specialistRepository, times(1)).findByFirstNameAndLastName("John", "Doe");
    }

    @Test
    void findByFirstNameAndLastName_ShouldThrowSpecialistOperationException_WhenExceptionOccurs() {
        doThrow(new RuntimeException()).when(specialistRepository).findByFirstNameAndLastName(anyString(), anyString());

        assertThrows(SpecialistOperationException.class, () -> specialistService.findByFirstNameAndLastName("John", "Doe"));
    }

    @Test
    void checkSpecialistImage_ShouldReturnTrue_WhenImageExists() throws SpecialistOperationException {
        Specialist specialist = generateValidSpecialist();
        specialist.setPersonalImage(new byte[10]);

        boolean result = specialistService.checkSpecialistImage(specialist);

        assertTrue(result);
    }

}
