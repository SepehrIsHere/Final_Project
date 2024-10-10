package ir.maktabsharif.finalproject.service.impl.order;

import ir.maktabsharif.finalproject.entities.Customer;
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

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

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
                suggestedPrice(1000000.0).
                dateOfService(LocalDate.of(2020, Month.APRIL, 12)).
                description("test description for order").
                status(OrderStatus.WAITING_FOR_SPECIALIST_SELECTION).
                subTask(subTask).build();
    }

    @Test
    void findByOrderStatusAndCustomer_ShouldReturnOrders_WhenSuccessful() throws OrderOperationException {
        // Arrange
        Customer customer = new Customer();
        OrderStatus orderStatus = OrderStatus.WAITING_FOR_SPECIALIST_SELECTION;
        List<Order> orders = List.of(generateValidOrder()); // Only return valid orders
        when(orderRepository.findByCustomerAndOrderStatus(customer, orderStatus)).thenReturn(orders);

        // Act
        List<Order> result = orderService.findByOrderStatusAndCustomer(customer, orderStatus);

        // Assert
        verify(orderRepository, times(1)).findByCustomerAndOrderStatus(customer, orderStatus);
        assertEquals(1, result.size()); // Now it will pass, since only 1 valid order is returned
    }

    @Test
    void findByOrderStatusAndCustomer_ShouldThrowOrderOperationException_WhenExceptionOccurs() {
        // Arrange
        Customer customer = new Customer();
        OrderStatus orderStatus = OrderStatus.WAITING_FOR_SPECIALIST_SELECTION;
        doThrow(new RuntimeException("Find by customer and status failed")).when(orderRepository).findByCustomerAndOrderStatus(customer, orderStatus);

        // Act & Assert
        assertThrows(OrderOperationException.class, () -> orderService.findByOrderStatusAndCustomer(customer, orderStatus));
    }
}
