package ir.maktabsharif.finalproject.service.impl.specialist;

import ir.maktabsharif.finalproject.entities.Specialist;
import ir.maktabsharif.finalproject.enumerations.Role;
import ir.maktabsharif.finalproject.enumerations.SpecialistStatus;
import ir.maktabsharif.finalproject.exception.SpecialistOperationException;
import ir.maktabsharif.finalproject.repository.SpecialistRepository;
import ir.maktabsharif.finalproject.service.impl.SpecialistServiceImpl;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class SpecialistFindsTest {
    @Mock
    private SpecialistRepository specialistRepository;

    @Mock
    private ValidationUtil validationUtil;

    @InjectMocks
    private SpecialistServiceImpl specialistService;

    Specialist generateValidSpecialist() {
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

    @Test
    void findAll_ShouldReturnAllSpecialists_WhenValid() throws SpecialistOperationException {
        List<Specialist> specialists = Arrays.asList(generateValidSpecialist(), generateValidSpecialist());
        when(specialistRepository.findAll()).thenReturn(specialists);

        List<Specialist> result = specialistService.findAll();

        assertEquals(2, result.size());
        verify(specialistRepository, times(1)).findAll();
    }

    @Test
    void findAll_ShouldThrowSpecialistOperationException_WhenExceptionOccurs() {
        doThrow(new RuntimeException()).when(specialistRepository).findAll();

        assertThrows(SpecialistOperationException.class, () -> specialistService.findAll());
    }

    @Test
    void findById_ShouldReturnSpecialist_WhenSpecialistExists() throws SpecialistOperationException {
        Specialist specialist = generateValidSpecialist();
        when(specialistRepository.findSpecialistById(anyInt())).thenReturn(specialist);

        Specialist result = specialistService.findById(1);

        assertNotNull(result);
        verify(specialistRepository, times(1)).findSpecialistById(1);
    }

    @Test
    void findById_ShouldThrowSpecialistOperationException_WhenExceptionOccurs() {
        doThrow(new RuntimeException()).when(specialistRepository).findSpecialistById(anyInt());

        assertThrows(SpecialistOperationException.class, () -> specialistService.findById(1));
    }
}
