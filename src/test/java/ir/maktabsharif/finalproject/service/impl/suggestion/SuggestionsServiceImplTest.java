package ir.maktabsharif.finalproject.service.impl.suggestion;

import ir.maktabsharif.finalproject.entities.*;
import ir.maktabsharif.finalproject.enumerations.Role;
import ir.maktabsharif.finalproject.enumerations.SpecialistStatus;
import ir.maktabsharif.finalproject.exception.SuggestionOperationException;
import ir.maktabsharif.finalproject.repository.SuggestionsRepository;
import ir.maktabsharif.finalproject.service.impl.SuggestionsServiceImpl;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class SuggestionsServiceImplTest {

    @Mock
    private SuggestionsRepository suggestionsRepository;

    @Mock
    private ValidationUtil validationUtil;

    @InjectMocks
    private SuggestionsServiceImpl suggestionsServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        suggestionsServiceImpl = new SuggestionsServiceImpl(suggestionsRepository, validationUtil);
    }

    @AfterEach
    void tearDown() {
        suggestionsRepository.deleteAll();
    }

    Suggestions generateValidSuggestion() {
        Specialist specialist = Specialist.builder().
                firstName("test firstname").
                lastName("test lastname").
                email("test@gmail.com").
                username("test username").
                password("testpass123").
                role(Role.SPECIALIST).
                score(0.0).
                specialistStatus(SpecialistStatus.APPROVED).
                build();

        SubTask subTask = new SubTask("test subtask", 500000.0, "test subtask description", new Task("test task name", "test task description"));
        Order order = Order.builder().
                suggestedPrice(10000.0).
                dateOfService(LocalDate.of(2020, Month.APRIL, 12)).
                subTask(subTask).build();

        return Suggestions.builder().
                suggestedPrice(1000000.0).
                suggestedDate(LocalDate.of(2024, 11, 12)).
                workTime(LocalTime.of(10, 12)).specialist(specialist).
                order(order).
                build();
    }

    Suggestions generateInvalidSuggestion() {
        Specialist specialist = Specialist.builder().
                firstName("test firstname").
                lastName("test lastname").
                email("test@gmail.com").
                username("test username").
                password("testpass123").
                role(Role.SPECIALIST).
                score(0.0).
                specialistStatus(SpecialistStatus.APPROVED).
                build();

        SubTask subTask = new SubTask("test subtask", 500000.0, "test subtask description", new Task("test task name", "test task description"));
        Order order = Order.builder().
                suggestedPrice(1000000.0).
                dateOfService(LocalDate.of(2020, Month.APRIL, 12)).
                subTask(subTask).build();

        return Suggestions.builder().
                suggestedPrice(1000000.0).
                order(order).
                specialist(specialist).
                build();
    }

    @Test
    void add_ShouldSaveSuggestion_WhenValid() throws SuggestionOperationException {
        // Arrange
        Suggestions suggestion = generateInvalidSuggestion();
        when(validationUtil.isValid(suggestion)).thenReturn(true);

        // Act
        suggestionsServiceImpl.add(suggestion);

        // Assert
        verify(suggestionsRepository, times(1)).save(suggestion);
    }

    @Test
    void add_ShouldThrowException_WhenInvalid() throws SuggestionOperationException {
        // Arrange
        Suggestions suggestion = generateInvalidSuggestion();
        when(validationUtil.isValid(suggestion)).thenReturn(false);

        // Act
        suggestionsServiceImpl.add(suggestion);

        // Assert
        verify(validationUtil, times(1)).displayViolations(suggestion);
        verify(suggestionsRepository, never()).save(any(Suggestions.class));
    }

    @Test
    void add_ShouldThrowException_WhenRepositoryThrowsException() {
        // Arrange
        Suggestions suggestion = generateInvalidSuggestion();
        when(validationUtil.isValid(suggestion)).thenReturn(true);
        doThrow(new RuntimeException("Repository failure")).when(suggestionsRepository).save(suggestion);

        // Act & Assert
        SuggestionOperationException thrown = assertThrows(SuggestionOperationException.class, () -> {
            suggestionsServiceImpl.add(suggestion);
        });
        assertEquals("An error occured while adding suggestion", thrown.getMessage());
    }

    @Test
    void update_ShouldSaveSuggestion() throws SuggestionOperationException {
        // Arrange
        Suggestions suggestion = generateValidSuggestion();

        // Act
        suggestionsServiceImpl.update(suggestion);

        // Assert
        verify(suggestionsRepository, times(1)).save(suggestion);
    }

    @Test
    void delete_ShouldDeleteSuggestion() throws SuggestionOperationException {
        // Arrange
        Suggestions suggestion = generateValidSuggestion();

        // Act
        suggestionsServiceImpl.delete(suggestion);

        // Assert
        verify(suggestionsRepository, times(1)).delete(suggestion);
    }

    @Test
    void findById_ShouldReturnSuggestion_WhenFound() throws SuggestionOperationException {
        // Arrange
        int id = 1;
        Suggestions suggestion = generateValidSuggestion();
        when(suggestionsRepository.findById(id)).thenReturn(suggestion);

        // Act
        Suggestions result = suggestionsServiceImpl.findById(id);

        // Assert
        assertEquals(suggestion, result);
        verify(suggestionsRepository, times(1)).findById(id);
    }

    @Test
    void findById_ShouldThrowException_WhenNotFound() {
        // Arrange
        int id = 1;
        when(suggestionsRepository.findById(id)).thenThrow(new RuntimeException("Not found"));

        // Act & Assert
        SuggestionOperationException thrown = assertThrows(SuggestionOperationException.class, () -> {
            suggestionsServiceImpl.findById(id);
        });
        assertEquals("An error occured while finding suggestion", thrown.getMessage());
    }

    @Test
    void findAll_ShouldReturnAllSuggestions() throws SuggestionOperationException {
        // Arrange
        List<Suggestions> suggestionsList = Arrays.asList(generateValidSuggestion(), generateValidSuggestion());
        when(suggestionsRepository.findAll()).thenReturn(suggestionsList);

        // Act
        List<Suggestions> result = suggestionsServiceImpl.findAll();

        // Assert
        assertEquals(suggestionsList, result);
        verify(suggestionsRepository, times(1)).findAll();
    }

    @Test
    void findByOrder_ShouldReturnSuggestionsForOrder() throws SuggestionOperationException {
        // Arrange
        Order order = new Order();
        List<Suggestions> suggestionsList = Arrays.asList(generateValidSuggestion(), generateValidSuggestion());
        when(suggestionsRepository.findByOrder(order)).thenReturn(suggestionsList);

        // Act
        List<Suggestions> result = suggestionsServiceImpl.findByOrder(order);

        // Assert
        assertEquals(suggestionsList, result);
        verify(suggestionsRepository, times(1)).findByOrder(order);
    }
}
