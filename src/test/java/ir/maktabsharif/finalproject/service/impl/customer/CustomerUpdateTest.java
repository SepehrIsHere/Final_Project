package ir.maktabsharif.finalproject.service.impl.customer;

import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.enumerations.Role;
import ir.maktabsharif.finalproject.exception.CustomerOperationException;
import ir.maktabsharif.finalproject.repository.CustomerRepository;
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

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CustomerUpdateTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ValidationUtil validationUtil;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerServiceImpl(validationUtil, customerRepository);
    }

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    Customer generateValidCustomer() {
        return Customer.builder().
                firstName("test firstname").
                lastName("test lastname").
                email("test@gmail.com")
                .username("test username").
                password("testpass123").
                role(Role.CUSTOMER).
                credit(BigDecimal.ZERO).
                build();
    }

    Customer generateInvalidCustomer() {
        return Customer.builder().
                firstName("test firstname").
                lastName("test lastname").
                email("testistest.com")
                .username("test username").
                password("test pass").
                role(Role.CUSTOMER).
                credit(BigDecimal.ZERO).
                build();
    }

    @Test
    void update_ShouldSaveCustomer_WhenValid() throws Exception {
        // Arrange
        Customer customer = generateValidCustomer();

        // Act
        customerService.update(customer);

        // Assert
        verify(customerRepository).save(customer);
    }

    @Test
    void update_ShouldThrowCustomerOperationException_WhenExceptionOccurs() {
        // Arrange
        Customer customer = generateInvalidCustomer();
        doThrow(new RuntimeException()).when(customerRepository).save(customer);

        // Act & Assert
        assertThrows(CustomerOperationException.class, () -> customerService.update(customer));
    }
}