package ir.maktabsharif.finalproject.service.impl.integration;

import ir.maktabsharif.finalproject.entities.*;
import ir.maktabsharif.finalproject.enumerations.OrderStatus;
import ir.maktabsharif.finalproject.enumerations.Role;
import ir.maktabsharif.finalproject.enumerations.SpecialistStatus;
import ir.maktabsharif.finalproject.exception.SuggestionOperationException;
import ir.maktabsharif.finalproject.repository.*;
import ir.maktabsharif.finalproject.service.SuggestionsService;
import ir.maktabsharif.finalproject.service.impl.SuggestionsServiceImpl;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class SuggestionsServiceImplIntegrationTest {

    @Autowired
    private SuggestionsService suggestionsService;

    @Autowired
    private SuggestionsRepository suggestionsRepository;

    @Autowired
    private ValidationUtil validationUtil;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SpecialistRepository specialistRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SubTaskRepository subTaskRepository;

    @Autowired
    private OrderRepository orderRepository;

    private Suggestions suggestion;

    @BeforeEach
    public void setup() {
        Task task = new Task("test first task","test first description");
        taskRepository.saveAndFlush(task);
        SubTask subTask = new SubTask("test subtask",new BigDecimal(1000000),"test subtask desciption",task);
        subTaskRepository.saveAndFlush(subTask);

        Customer customer = Customer.builder()
                .firstName("test customer firstname")
                .lastName("test customer lastname")
                .email("testcustomer@gmail.com")
                .username("test customerusername")
                .password("testcustomerpas123")
                .role(Role.CUSTOMER)
                .credit(BigDecimal.ZERO)
                .build();
        customerRepository.saveAndFlush(customer);

        Specialist specialist = Specialist.builder()
                .firstName("test specialist firstname")
                .lastName("test specialist lastname")
                .email("testspecialist@gmail.com")
                .username("test specialistusername")
                .password("testspecialistpass123")
                .role(Role.SPECIALIST)
                .specialistStatus(SpecialistStatus.PENDING_APPROVAL)
                .score(0.0)
                .build();
        specialistRepository.saveAndFlush(specialist);

        Order order = Order.builder().
                specialist(specialist).
                customer(customer).
                subTask(subTask).
                suggestedPrice(new BigDecimal(10000)).
                dateOfService(LocalDate.of(2024,10,12)).
                status(OrderStatus.WAITING_FOR_SPECIALIST_SELECTION).
                description("test order description").build();
        orderRepository.saveAndFlush(order);

        suggestion = Suggestions.builder()
                .order(order)
                .specialist(specialist)
                .suggestedPrice(new BigDecimal("5000"))
                .suggestedDate(LocalDate.now())
                .workTime(LocalTime.now())
                .build();

        suggestionsService = new SuggestionsServiceImpl(suggestionsRepository,validationUtil);
    }

    @AfterEach
    void tearDown(){
        suggestionsRepository.deleteAll();
    }

    @Test
    public void testAddSuggestion() throws SuggestionOperationException {
        suggestionsService.add(suggestion);

        Optional<Suggestions> savedSuggestion = suggestionsRepository.findById(suggestion.getId());
        assertTrue(savedSuggestion.isPresent());
        assertEquals(suggestion.getSuggestedPrice(), savedSuggestion.get().getSuggestedPrice());
    }

    @Test
    public void testUpdateSuggestion() throws SuggestionOperationException {
        suggestionsService.add(suggestion);

        Optional<Suggestions> savedSuggestion = suggestionsRepository.findById(suggestion.getId());
        assertTrue(savedSuggestion.isPresent());

        Suggestions updatedSuggestion = savedSuggestion.get();
        updatedSuggestion.setSuggestedPrice(new BigDecimal("6000"));
        suggestionsService.update(updatedSuggestion);

        Optional<Suggestions> updatedSuggestionOpt = suggestionsRepository.findById(updatedSuggestion.getId());
        assertTrue(updatedSuggestionOpt.isPresent());
        assertEquals(new BigDecimal("6000"), updatedSuggestionOpt.get().getSuggestedPrice());
    }

    @Test
    public void testDeleteSuggestion() throws SuggestionOperationException {
        suggestionsService.add(suggestion);

        suggestionsService.delete(suggestion);
        Optional<Suggestions> deletedSuggestion = suggestionsRepository.findById(suggestion.getId());
        assertFalse(deletedSuggestion.isPresent());
    }

    @Test
    public void testFindById() throws SuggestionOperationException {
        suggestionsService.add(suggestion);

        Optional<Suggestions> foundSuggestion = Optional.ofNullable(suggestionsService.findById(suggestion.getId()));
        assertTrue(foundSuggestion.isPresent());
        assertEquals(suggestion.getSuggestedPrice(), foundSuggestion.get().getSuggestedPrice());
    }

    @Test
    public void testFindAllSuggestions() throws SuggestionOperationException {
        suggestionsService.add(suggestion);

        List<Suggestions> allSuggestions = suggestionsService.findAll();
        assertFalse(allSuggestions.isEmpty());
        assertTrue(allSuggestions.contains(suggestion));
    }

    @Test
    public void testFindByOrder() throws SuggestionOperationException {
        suggestionsService.add(suggestion);

        List<Suggestions> suggestionsByOrder = suggestionsService.findByOrder(suggestion.getOrder());
        assertFalse(suggestionsByOrder.isEmpty());
        assertTrue(suggestionsByOrder.contains(suggestion));
    }
}
