package ir.maktabsharif.finalproject.service.impl.integration;

import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.enumerations.Role;
import ir.maktabsharif.finalproject.exception.CustomerOperationException;
import ir.maktabsharif.finalproject.repository.CustomerRepository;
import ir.maktabsharif.finalproject.service.OrderService;
import ir.maktabsharif.finalproject.service.SuggestionsService;
import ir.maktabsharif.finalproject.service.impl.CustomerServiceImpl;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CustomerServiceImplIntegrationTest {

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Mock
    private OrderService orderService;

    @Mock
    private SuggestionsService suggestionsService;

    @Autowired
    private ValidationUtil validationUtil;

    private Customer validCustomer;

    @BeforeEach
    void setUp() {
        validCustomer = Customer.builder()
                .firstName("test firstname")
                .lastName("test lastname")
                .email("test@gmail.com")
                .username("test username")
                .password("testpas123")
                .role(Role.CUSTOMER)
                .credit(BigDecimal.ZERO)
                .build();
        customerService = new CustomerServiceImpl(validationUtil, customerRepository,orderService,suggestionsService);
    }

    @AfterEach
    void tearDown(){
        customerRepository.deleteAll();
    }

    @Test
    void add_ShouldSaveCustomer_WhenValid() throws CustomerOperationException {
        // Act
        customerService.add(validCustomer);

        // Assert
        Customer savedCustomer = customerRepository.findCustomerById(validCustomer.getId());
        assertNotNull(savedCustomer);
        assertEquals("test firstname", savedCustomer.getFirstName());
        assertEquals("test lastname", savedCustomer.getLastName());
    }

    @Test
    void findAll_ShouldReturnAllCustomers() throws CustomerOperationException {
        // Arrange
        customerService.add(validCustomer);

        // Act
        List<Customer> customers = customerService.findAll();

        // Assert
        assertTrue(customers.contains(validCustomer));
    }

    @Test
    void findById_ShouldReturnCustomer_WhenExists() throws CustomerOperationException {
        // Arrange
        customerService.add(validCustomer);

        // Act
        Customer foundCustomer = customerService.findById(validCustomer.getId());

        // Assert
        assertNotNull(foundCustomer);
        assertEquals("test firstname", foundCustomer.getFirstName());
        assertEquals("test lastname", foundCustomer.getLastName());
    }

    @Test
    void findByFirstNameAndLastName_ShouldReturnCustomer_WhenValidName() throws CustomerOperationException {
        // Arrange
        customerService.add(validCustomer);

        // Act
        Customer foundCustomer = customerService.findByFirstNameAndLastName("test firstname", "test lastname");

        // Assert
        assertNotNull(foundCustomer);
        assertEquals("test firstname", foundCustomer.getFirstName());
        assertEquals("test lastname", foundCustomer.getLastName());
    }

    @Test
    void delete_ShouldRemoveCustomer_WhenValid() throws CustomerOperationException {
        // Arrange
        customerService.add(validCustomer);

        // Act
        customerService.delete(validCustomer);

        // Assert
        Customer deletedCustomer = customerRepository.findCustomerById(validCustomer.getId());
        assertNull(deletedCustomer);
    }
}
