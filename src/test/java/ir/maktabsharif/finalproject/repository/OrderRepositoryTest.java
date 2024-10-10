package ir.maktabsharif.finalproject.repository;

import com.github.javafaker.Faker;
import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.entities.SubTask;
import ir.maktabsharif.finalproject.entities.Task;
import ir.maktabsharif.finalproject.enumerations.OrderStatus;
import ir.maktabsharif.finalproject.enumerations.Role;
import ir.maktabsharif.finalproject.util.AppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@Import(AppConfig.class)
class OrderRepositoryTest {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final TaskRepository taskRepository;
    private final SubTaskRepository subTaskRepository;
    private final Faker faker;

    @Autowired
    public OrderRepositoryTest(OrderRepository orderRepository, CustomerRepository customerRepository, TaskRepository taskRepository, SubTaskRepository subTaskRepository, Faker faker) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.taskRepository = taskRepository;
        this.subTaskRepository = subTaskRepository;
        this.faker = faker;
    }

    String generateValidPassword() {
        return faker.regexify("[a-zA-Z]{5}[0-9]{3}");
    }

    @Test
    void findsByCustomer() {
        Task task = new Task("test task", "test task description");
        taskRepository.save(task);

        SubTask subTask = new SubTask("Test subtask", 10000.0, "test description",task);
        subTaskRepository.save(subTask);
        Customer customer = Customer.builder().
                firstName(faker.name().firstName()).
                lastName(faker.name().lastName()).
                email(faker.internet().emailAddress()).
                username(faker.name().username()).
                password(generateValidPassword()).
                role(Role.CUSTOMER).credit(BigDecimal.ZERO)
                .build();
        customerRepository.save(customer);
        Order order = Order.builder().
                suggestedPrice(200000.0)
                .dateOfService(LocalDate.of(2024, Month.APRIL, 12)).
                description("test description").
                status(OrderStatus.WAITING_FOR_SPECIALIST_SELECTION).
                subTask(subTask).
                customer(customer).
                build();
        orderRepository.save(order);

        Order expectedOrder = orderRepository.findByCustomer(customer);
        assertNotNull(expectedOrder);
        assertEquals(expectedOrder.getId(), order.getId());
        assertEquals(expectedOrder.getCustomer(), customer);
        assertEquals(expectedOrder.getStatus(), OrderStatus.WAITING_FOR_SPECIALIST_SELECTION);
        assertEquals(expectedOrder.getDateOfService(), LocalDate.of(2024, Month.APRIL, 12));
        assertEquals(expectedOrder.getDescription(), order.getDescription());
        assertEquals(expectedOrder.getSubTask(), subTask);
    }

    @Test
    void doesNotFindByCustomer() {
        Customer customer = Customer.builder().
                firstName(faker.name().firstName()).
                lastName(faker.name().lastName()).
                email(faker.internet().emailAddress()).
                username(faker.name().username()).
                password(generateValidPassword()).
                role(Role.CUSTOMER).credit(BigDecimal.ZERO)
                .build();
        customerRepository.save(customer);
        Order order = orderRepository.findByCustomer(customer);
        assertNull(order);
    }

    @Test
    void findsByCustomerId() {
        Task task = new Task("test task", "test task description");
        taskRepository.save(task);

        SubTask subTask = new SubTask("Test subtask", 10000.0, "test description",task);
        subTaskRepository.save(subTask);
        Customer customer = Customer.builder().
                firstName(faker.name().firstName()).
                lastName(faker.name().lastName()).
                email(faker.internet().emailAddress()).
                username(faker.name().username()).
                password(generateValidPassword()).
                role(Role.CUSTOMER).credit(BigDecimal.ZERO)
                .build();
        customerRepository.save(customer);
        Order order = Order.builder().
                suggestedPrice(200000.0)
                .dateOfService(LocalDate.of(2024, Month.APRIL, 12)).
                description("test description").
                status(OrderStatus.WAITING_FOR_SPECIALIST_SELECTION).
                subTask(subTask).
                customer(customer).
                build();

        orderRepository.save(order);

        Order expectedOrder = orderRepository.findByCustomerId(customer.getId());

        assertNotNull(expectedOrder);
        assertEquals(expectedOrder.getId(), order.getId());
        assertEquals(expectedOrder.getCustomer(), customer);
        assertEquals(expectedOrder.getStatus(), OrderStatus.WAITING_FOR_SPECIALIST_SELECTION);
        assertEquals(expectedOrder.getDateOfService(), LocalDate.of(2024, Month.APRIL, 12));
        assertEquals(expectedOrder.getDescription(), order.getDescription());
        assertEquals(expectedOrder.getSubTask(), subTask);
    }

    @Test
    void doesNotFindByCustomerId() {
        int customerId = 123;
        Order expectedOrder = orderRepository.findByCustomerId(customerId);
        assertNull(expectedOrder);
    }

    @Test
    void findByCustomerAndOrderStatus() {
        Task task = new Task("test task", "test task description");
        taskRepository.save(task);

        SubTask subTask = new SubTask("Test subtask", 10000.0, "test description",task);
        subTaskRepository.save(subTask);
        SubTask subTask1 = new SubTask("Test subtask1", 10000.0, "test description1",task);
        subTaskRepository.save(subTask1);
        SubTask subTask2 = new SubTask("Test subtask2", 10000.0, "test description2",task);
        subTaskRepository.save(subTask2);
        Customer customer = Customer.builder().
                firstName(faker.name().firstName()).
                lastName(faker.name().lastName()).
                email(faker.internet().emailAddress()).
                username(faker.name().username()).
                password(generateValidPassword()).
                role(Role.CUSTOMER).credit(BigDecimal.ZERO)
                .build();
        customerRepository.save(customer);
        Order order = Order.builder().
                suggestedPrice(200000.0)
                .dateOfService(LocalDate.of(2024, Month.APRIL, 12)).
                description("test description").
                status(OrderStatus.WAITING_FOR_SPECIALIST_PROPOSALS).
                subTask(subTask).
                customer(customer).
                build();
        Order order1 = Order.builder().
                suggestedPrice(200000.0)
                .dateOfService(LocalDate.of(2024, Month.APRIL, 12)).
                description("test description").
                status(OrderStatus.WAITING_FOR_SPECIALIST_PROPOSALS).
                subTask(subTask1).
                customer(customer).
                build();
        Order order2 = Order.builder().
                suggestedPrice(200000.0)
                .dateOfService(LocalDate.of(2024, Month.APRIL, 12)).
                description("test description").
                status(OrderStatus.WAITING_FOR_SPECIALIST_PROPOSALS).
                subTask(subTask2).
                customer(customer).
                build();
        orderRepository.save(order);
        orderRepository.save(order1);
        orderRepository.save(order2);

        List<Order> expectedOrders = orderRepository.findByCustomerAndOrderStatus(customer, OrderStatus.WAITING_FOR_SPECIALIST_PROPOSALS);

        assertNotNull(expectedOrders);
        assertEquals(expectedOrders.size(), 2);
        assertEquals(expectedOrders.get(0).getId(), order.getId());
        assertEquals(expectedOrders.get(0).getCustomer(), order.getCustomer());
        assertEquals(expectedOrders.get(1).getId(), order1.getId());
        assertEquals(expectedOrders.get(1).getCustomer(), order1.getCustomer());
        assertEquals(expectedOrders.get(2).getId(), order2.getId());
        assertEquals(expectedOrders.get(2).getCustomer(), order2.getCustomer());

    }

    @Test
    void doesNotFindByCustomerAndOrderStatus() {
        Customer customer = Customer.builder().
                firstName(faker.name().firstName()).
                lastName(faker.name().lastName()).
                email(faker.internet().emailAddress()).
                username(faker.name().username()).
                password(generateValidPassword()).
                role(Role.CUSTOMER).credit(BigDecimal.ZERO)
                .build();
        customer.setId(1234);
        customerRepository.save(customer);
        List<Order> expectedOrders = orderRepository.findByCustomerAndOrderStatus(customer, OrderStatus.WAITING_FOR_SPECIALIST_SELECTION);

        assertTrue(expectedOrders.isEmpty());
    }
}