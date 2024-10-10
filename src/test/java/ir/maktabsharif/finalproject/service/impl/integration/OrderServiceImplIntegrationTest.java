package ir.maktabsharif.finalproject.service.impl.integration;

import ir.maktabsharif.finalproject.entities.*;
import ir.maktabsharif.finalproject.enumerations.OrderStatus;
import ir.maktabsharif.finalproject.enumerations.Role;
import ir.maktabsharif.finalproject.enumerations.SpecialistStatus;
import ir.maktabsharif.finalproject.exception.OrderOperationException;
import ir.maktabsharif.finalproject.repository.*;
import ir.maktabsharif.finalproject.service.OrderService;
import ir.maktabsharif.finalproject.service.impl.OrderServiceImpl;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class OrderServiceImplIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SpecialistRepository specialistRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SubTaskRepository subTaskRepository;

    @Autowired
    private ValidationUtil validationUtil;

    private Order order;
    private Customer customer;

    @BeforeEach
    public void setup() {
        Task task = new Task("test first task", "test first description");
        taskRepository.saveAndFlush(task);
        SubTask subTask = new SubTask("test subtask", 1000000.0, "test subtask desciption", task);
        subTaskRepository.saveAndFlush(subTask);

         customer = Customer.builder()
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

         order = Order.builder().
                specialist(specialist).
                customer(customer).
                subTask(subTask).
                suggestedPrice(100000.0).
                dateOfService(LocalDate.of(2024, 10, 12)).
                status(OrderStatus.WAITING_FOR_SPECIALIST_SELECTION).
                description("test order description").build();
//        orderService = new OrderServiceImpl(validationUtil, orderRepository, customerRepository);
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }

    @Test
    public void testAddOrder() throws OrderOperationException {
        orderService.add(order);

        Optional<Order> savedOrder = orderRepository.findById(order.getId());
        assertTrue(savedOrder.isPresent());
        assertEquals(order.getDescription(), savedOrder.get().getDescription());
    }

    @Test
    public void testUpdateOrder() throws OrderOperationException {
        orderService.add(order);

        Optional<Order> savedOrder = orderRepository.findById(order.getId());
        assertTrue(savedOrder.isPresent());

        Order updatedOrder = savedOrder.get();
        updatedOrder.setDescription("Updated Test Order");
        orderService.update(updatedOrder);

        Optional<Order> updatedOrderOpt = orderRepository.findById(updatedOrder.getId());
        assertTrue(updatedOrderOpt.isPresent());
        assertEquals("Updated Test Order", updatedOrderOpt.get().getDescription());
    }

    @Test
    public void testDeleteOrder() throws OrderOperationException {
        orderService.add(order);

        orderService.delete(order);
        Optional<Order> deletedOrder = orderRepository.findById(order.getId());
        assertFalse(deletedOrder.isPresent());
    }

    @Test
    public void testFindById() throws OrderOperationException {
        orderService.add(order);

        Optional<Order> foundOrder = orderRepository.findById(order.getId());
        assertTrue(foundOrder.isPresent());
        assertEquals(order.getDescription(), foundOrder.get().getDescription());
    }

    @Test
    public void testFindAllOrders() throws OrderOperationException {
        orderService.add(order);

        List<Order> allOrders = orderService.findAll();
        assertFalse(allOrders.isEmpty());
        assertTrue(allOrders.contains(order));
    }

    @Test
    public void testFindByOrderStatusAndCustomer() throws OrderOperationException {
        orderService.add(order);

        List<Order> ordersByStatusAndCustomer = orderService.findByOrderStatusAndCustomer(order.getCustomer(), OrderStatus.WAITING_FOR_SPECIALIST_SELECTION);
        assertFalse(ordersByStatusAndCustomer.isEmpty());
        assertTrue(ordersByStatusAndCustomer.contains(order));
    }
}
