package ir.maktabsharif.finalproject.service.impl.order;

import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.entities.SubTask;
import ir.maktabsharif.finalproject.entities.Task;
import ir.maktabsharif.finalproject.enumerations.OrderStatus;
import ir.maktabsharif.finalproject.exception.OrderOperationException;
import ir.maktabsharif.finalproject.repository.CustomerRepository;
import ir.maktabsharif.finalproject.repository.OrderRepository;
import ir.maktabsharif.finalproject.service.impl.OrderServiceImpl;
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
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class AddOrderTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ValidationUtil validationUtil;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }

    Order generateValidOrder() {
        SubTask subTask = new SubTask("test subtask", 500000.0, "test subtask description", new Task("test task name", "test task description"));
        return Order.builder().
                suggestedPrice(500000.0).
                dateOfService(LocalDate.of(2020, Month.APRIL, 12)).
                description("test description for order").
                status(OrderStatus.WAITING_FOR_SPECIALIST_SELECTION).
                subTask(subTask).build();
    }

    Order generateInvalidOrder() {
        SubTask subTask = new SubTask("test subtask", 500000.0, "test subtask description", new Task("test task name", "test task description"));
        return Order.builder().
                suggestedPrice(500000.0).
                dateOfService(LocalDate.of(2020, Month.APRIL, 12)).
                subTask(subTask).build();
    }

    @Test
    void add_ShouldSaveOrder_WhenValid() throws OrderOperationException {
        // Arrange
        Order order = generateValidOrder();
        when(validationUtil.isValid(order)).thenReturn(true);

        // Act
        orderService.add(order);

        // Assert
        verify(orderRepository).save(order);
    }

    @Test
    void add_ShouldDisplayViolations_WhenInvalid() throws OrderOperationException {
        // Arrange
        Order order = generateInvalidOrder();
        when(validationUtil.isValid(order)).thenReturn(false);

        // Act
        orderService.add(order);

        // Assert
        verify(validationUtil).displayViolations(order);
        verify(orderRepository, never()).save(order);
    }

    @Test
    void add_ShouldThrowOrderOperationException_WhenExceptionOccurs() {
        // Arrange
        Order order = generateValidOrder();
        when(validationUtil.isValid(order)).thenReturn(true);
        doThrow(new RuntimeException("Save failed")).when(orderRepository).save(order);

        // Act & Assert
        assertThrows(OrderOperationException.class, () -> orderService.add(order));
    }

}
