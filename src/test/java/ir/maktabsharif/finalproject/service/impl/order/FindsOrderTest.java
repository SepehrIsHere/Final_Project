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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class FindsOrderTest {

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
        orderService = new OrderServiceImpl(validationUtil, orderRepository, customerRepository);
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }

    Order generateValidOrder() {
        SubTask subTask = new SubTask("test subtask", new BigDecimal(500000), "test subtask description", new Task("test task name", "test task description"));
        return Order.builder().
                suggestedPrice(new BigDecimal(1000000)).
                dateOfService(LocalDate.of(2020, Month.APRIL, 12)).
                description("test description for order").
                status(OrderStatus.WAITING_FOR_SPECIALIST_SELECTION).
                subTask(subTask).build();
    }

    Order generateInvalidOrder() {
        SubTask subTask = new SubTask("test subtask", new BigDecimal(500000), "test subtask description", new Task("test task name", "test task description"));
        return Order.builder().
                suggestedPrice(new BigDecimal(1000000)).
                dateOfService(LocalDate.of(2020, Month.APRIL, 12)).
                subTask(subTask).build();
    }

    @Test
    void findAll_ShouldReturnListOfOrders_WhenSuccessful() throws OrderOperationException {
        // Arrange
        List<Order> orders = List.of(generateValidOrder(), generateInvalidOrder());
        when(orderRepository.findAll()).thenReturn(orders);

        // Act
        List<Order> result = orderService.findAll();

        // Assert
        verify(orderRepository).findAll();
        assertEquals(2, result.size());
    }

    @Test
    void findAll_ShouldThrowOrderOperationException_WhenExceptionOccurs() {
        // Arrange
        doThrow(new RuntimeException("Find all failed")).when(orderRepository).findAll();

        // Act & Assert
        assertThrows(OrderOperationException.class, () -> orderService.findAll());
    }

    @Test
    void findById_ShouldReturnOrder_WhenFound() throws OrderOperationException {
        // Arrange
        Order order = generateValidOrder();
        when(orderRepository.findByCustomerId(1)).thenReturn(order);

        // Act
        Order result = orderService.findById(1);

        // Assert
        verify(orderRepository).findByCustomerId(1);
        assertEquals(order, result);
    }

    @Test
    void findById_ShouldThrowOrderOperationException_WhenExceptionOccurs() {
        // Arrange
        doThrow(new RuntimeException("Find by ID failed")).when(orderRepository).findByCustomerId(1);

        // Act & Assert
        assertThrows(OrderOperationException.class, () -> orderService.findById(1));
    }

}
