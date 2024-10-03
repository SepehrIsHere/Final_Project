package ir.maktabsharif.finalproject.service.impl.integration;

import ir.maktabsharif.finalproject.entities.Specialist;
import ir.maktabsharif.finalproject.enumerations.Role;
import ir.maktabsharif.finalproject.enumerations.SpecialistStatus;
import ir.maktabsharif.finalproject.exception.SpecialistOperationException;
import ir.maktabsharif.finalproject.repository.SpecialistRepository;
import ir.maktabsharif.finalproject.service.impl.SpecialistServiceImpl;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class SpecialistServiceImplIntegrationTest {

    @Autowired
    private SpecialistServiceImpl specialistService;

    @Autowired
    private SpecialistRepository specialistRepository;

    @Autowired
    private ValidationUtil validationUtil;

    private Specialist validSpecialist;

    @BeforeEach
    void setUp() {
        validSpecialist = Specialist.builder()
                .firstName("test firstname")
                .lastName("test lastname")
                .email("test@gmail.com")
                .username("test username")
                .password("testpass123")
                .role(Role.SPECIALIST)
                .specialistStatus(SpecialistStatus.PENDING_APPROVAL)
                .score(0.0)
                .build();
        specialistService = new SpecialistServiceImpl(validationUtil, specialistRepository);
    }

    @AfterEach
    void tearDown(){
        specialistRepository.deleteAll();
    }

    @Test
    void add_ShouldSaveSpecialist_WhenValid() throws SpecialistOperationException {
        // Act
        specialistService.add(validSpecialist);

        // Assert
        Specialist savedSpecialist = specialistRepository.findSpecialistById(validSpecialist.getId());
        assertNotNull(savedSpecialist);
        assertEquals("test firstname", savedSpecialist.getFirstName());
        assertEquals("test lastname", savedSpecialist.getLastName());
    }

    @Test
    void findAll_ShouldReturnAllSpecialists() throws SpecialistOperationException {
        // Arrange
        specialistService.add(validSpecialist);

        // Act
        List<Specialist> specialists = specialistService.findAll();

        // Assert
        assertTrue(specialists.contains(validSpecialist));
    }

    @Test
    void findById_ShouldReturnSpecialist_WhenExists() throws SpecialistOperationException {
        // Arrange
        specialistService.add(validSpecialist);

        // Act
        Specialist foundSpecialist = specialistService.findById(validSpecialist.getId());

        // Assert
        assertNotNull(foundSpecialist);
        assertEquals("test firstname", foundSpecialist.getFirstName());
        assertEquals("test lastname", foundSpecialist.getLastName());
    }

    @Test
    void findByFirstNameAndLastName_ShouldReturnSpecialist_WhenValidName() throws SpecialistOperationException {
        // Arrange
        specialistService.add(validSpecialist);

        // Act
        Specialist foundSpecialist = specialistService.findByFirstNameAndLastName("test firstname", "test lastname");

        // Assert
        assertNotNull(foundSpecialist);
        assertEquals("test firstname", foundSpecialist.getFirstName());
        assertEquals("test lastname", foundSpecialist.getLastName());
    }

    @Test
    void delete_ShouldRemoveSpecialist_WhenValid() throws SpecialistOperationException {
        // Arrange
        specialistService.add(validSpecialist);

        // Act
        specialistService.delete(validSpecialist);

        // Assert
        Specialist deletedSpecialist = specialistRepository.findSpecialistById(validSpecialist.getId());
        assertNull(deletedSpecialist);
    }

    @Test
    void checkSpecialistImage_ShouldReturnFalse_WhenNoImage() throws SpecialistOperationException {
        // Act
        boolean hasImage = specialistService.checkSpecialistImage(validSpecialist);

        // Assert
        assertFalse(hasImage);
    }

    @Test
    void checkSpecialistImage_ShouldReturnTrue_WhenImageExists() throws SpecialistOperationException {
        // Arrange
        validSpecialist.setPersonalImage(new byte[]{1, 2, 3}); // Simulating a byte array for image
        specialistService.add(validSpecialist);

        // Act
        boolean hasImage = specialistService.checkSpecialistImage(validSpecialist);

        // Assert
        assertTrue(hasImage);
    }
}
