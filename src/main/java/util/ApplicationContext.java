package util;

import entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Getter;
import repository.*;
import repository.impl.*;
import service.*;
import service.impl.*;

public class ApplicationContext {
    private static ApplicationContext applicationContext;
    private EntityManagerFactory emf;
    private EntityManager em;
    @Getter
    private final UsersRepository usersRepository;
    @Getter
    private final CommentRepository commentRepository;
    @Getter
    private final CustomerRepository customerRepository;
    @Getter
    private final OrderRepository orderRepository;
    @Getter
    private final SpecialistRepository specialistRepository;
    @Getter
    private final SubTaskRepository subTaskRepository;
    @Getter
    private final TaskRepository taskRepository;
    @Getter
    private final UsersService usersService;
    @Getter
    private final CommentService commentService;
    @Getter
    private final CustomerService customerService;
    @Getter
    private final OrderService orderService;
    @Getter
    private final SpecialistService specialistService;
    @Getter
    private final SubTaskService subTaskService;
    @Getter
    private final TaskService taskService;

    private ApplicationContext() {
        emf = Persistence.createEntityManagerFactory("default");
        em = emf.createEntityManager();

        usersRepository = new UsersRepositoryImpl<>(em);
        commentRepository = new CommentRepositoryImpl<>(em);
        customerRepository = new CustomerRepositoryImpl<>(em);
        orderRepository = new OrderRepositoryImpl<>(em);
        specialistRepository = new SpecialistRepositoryImpl<>(em);
        subTaskRepository = new SubTaskRepositoryImpl<>(em);
        taskRepository = new TaskRepositoryImpl<>(em);

        usersService = new UsersServiceImpl(usersRepository);
        commentService = new CommentServiceImpl(commentRepository);
        customerService = new CustomerServiceImpl(customerRepository);
        orderService = new OrderServiceImpl(orderRepository);
        specialistService = new SpecialistServiceImpl(specialistRepository);
        subTaskService = new SubTaskServiceImpl(subTaskRepository);
        taskService = new TaskServiceImpl(taskRepository);

    }

    public static ApplicationContext getInstance() {
        if (applicationContext == null) {
            applicationContext = new ApplicationContext();
        }
        return applicationContext;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("default");
        }
        return emf;
    }

    public EntityManager getEntityManager() {
        if (em == null) {
            em = emf.createEntityManager();
        }
        return em;
    }
}
