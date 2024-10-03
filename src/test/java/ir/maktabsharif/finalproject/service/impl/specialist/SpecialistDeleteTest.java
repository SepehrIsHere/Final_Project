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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpecialistDeleteTest {
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

    Specialist generateInvalidSpecialist(){
        return Specialist.
                builder().
                firstName("test firstname").
                lastName("test lastname").
                email("testmail.com").
                username("test username").
                password("test pass").
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
    void delete_ShouldDeleteSpecialist_WhenValid() throws SpecialistOperationException {
        Specialist specialist = generateValidSpecialist();
        specialistService.delete(specialist);

        verify(specialistRepository, times(1)).delete(specialist);
    }

    @Test
    void delete_ShouldThrowSpecialistOperationException_WhenExceptionOccurs() {
        Specialist specialist = generateInvalidSpecialist();
        doThrow(new RuntimeException()).when(specialistRepository).delete(specialist);

        assertThrows(SpecialistOperationException.class, () -> specialistService.delete(specialist));
    }

}
