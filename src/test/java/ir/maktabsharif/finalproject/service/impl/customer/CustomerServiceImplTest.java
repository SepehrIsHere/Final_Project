package ir.maktabsharif.finalproject.service.impl.customer;

import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.enumerations.Role;
import ir.maktabsharif.finalproject.exception.CustomerOperationException;
import ir.maktabsharif.finalproject.repository.CustomerRepository;
import ir.maktabsharif.finalproject.service.OrderService;
import ir.maktabsharif.finalproject.service.SuggestionsService;
import ir.maktabsharif.finalproject.service.impl.CustomerServiceImpl;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ValidationUtil validationUtil;

    @Mock
    private OrderService orderService;

    @Mock
    private SuggestionsService suggestionsService;


    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerServiceImpl(validationUtil, customerRepository,orderService,suggestionsService);
    }

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    Customer generateValidCustomer() {
        return Customer.builder().
                firstName("John").
                lastName("Doe").
                email("test@gmail.com")
                .username("test username").
                password("testpass123").
                role(Role.CUSTOMER).
                credit(BigDecimal.ZERO).
                build();
    }

    @Test
    void findByFirstNameAndLastName_ShouldReturnCustomer_WhenSuccessful() throws Exception {
        // Arrange
        Customer customer = generateValidCustomer();
        when(customerRepository.findCustomerByFirstNameAndLastName("John", "Doe")).thenReturn(customer);

        // Act
        Customer result = customerService.findByFirstNameAndLastName("John", "Doe");

        // Assert
        verify(customerRepository).findCustomerByFirstNameAndLastName("John", "Doe");
        assertThat(result).isNotNull();
    }

    @Test
    void findByFirstNameAndLastName_ShouldThrowCustomerOperationException_WhenExceptionOccurs() {
        // Arrange
        doThrow(new RuntimeException()).when(customerRepository).findCustomerByFirstNameAndLastName("John", "Doe");

        // Act & Assert
        assertThrows(CustomerOperationException.class, () -> customerService.findByFirstNameAndLastName("John", "Doe"));
    }
}
