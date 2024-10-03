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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DeleteOrderTest {

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
        orderService = new OrderServiceImpl(validationUtil,orderRepository,customerRepository);
    }

    @AfterEach
    void tearDown(){
        orderRepository.deleteAll();
    }

    Order generateValidOrder(){
        SubTask subTask = new SubTask("test subtask",new BigDecimal(500000),"test subtask description",new Task("test task name","test task description"));
        return Order.builder().
                suggestedPrice(new BigDecimal(1000000)).
                dateOfService(LocalDate.of(2020, Month.APRIL,12)).
                description("test description for order").
                status(OrderStatus.WAITING_FOR_SPECIALIST_SELECTION).
                subTask(subTask).build();
    }

    Order generateInvalidOrder(){
        SubTask subTask = new SubTask("test subtask",new BigDecimal(500000),"test subtask description",new Task("test task name","test task description"));
        return Order.builder().
                suggestedPrice(new BigDecimal(1000000)).
                dateOfService(LocalDate.of(2020, Month.APRIL,12)).
                subTask(subTask).build();
    }

    @Test
    void delete_ShouldDeleteOrder_WhenCalled() throws OrderOperationException {
        // Arrange
        Order order = generateValidOrder();

        // Act
        orderService.delete(order);

        // Assert
        verify(orderRepository).delete(order);
    }

    @Test
    void delete_ShouldThrowOrderOperationException_WhenExceptionOccurs() {
        // Arrange
        Order order = generateInvalidOrder();
        doThrow(new RuntimeException("Delete failed")).when(orderRepository).delete(order);

        // Act & Assert
        assertThrows(OrderOperationException.class, () -> orderService.delete(order));
    }

}
