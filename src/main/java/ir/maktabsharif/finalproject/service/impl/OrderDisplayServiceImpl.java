package ir.maktabsharif.finalproject.service.impl;

import ir.maktabsharif.finalproject.entities.*;
import ir.maktabsharif.finalproject.enumerations.OrderStatus;
import ir.maktabsharif.finalproject.exception.CustomerOperationException;
import ir.maktabsharif.finalproject.exception.SpecialistOperationException;
import ir.maktabsharif.finalproject.exception.SubTaskOperationException;
import ir.maktabsharif.finalproject.exception.TaskOperationException;
import ir.maktabsharif.finalproject.repository.OrderRepository;
import ir.maktabsharif.finalproject.service.*;
import ir.maktabsharif.finalproject.specification.OrderSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDisplayServiceImpl implements OrderDisplayService {
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final TaskService taskService;
    private final SubTaskService subTaskService;
    private final CustomerService customerService;
    private final SpecialistService specialistService;

    @Override
    public List<Order> getCustomerHistory(String customerFirstName, String customerLastName) throws CustomerOperationException {
        Customer customer = customerService.findByFirstNameAndLastName(customerFirstName, customerLastName);
        OrderSpecification orderSpecification = new OrderSpecification(customer, null, null, null, null, null, null, null, null);
        return orderRepository.findAll(orderSpecification);
    }

    @Override
    public List<Order> getSpecialistHistory(String specialistFirstName, String specialistLastName) throws SpecialistOperationException {
        Specialist specialist = specialistService.findByFirstNameAndLastName(specialistFirstName, specialistLastName);
        OrderSpecification orderSpecification = new OrderSpecification(null, specialist, null, null, null, null, null, null, null);
        return orderRepository.findAll(orderSpecification);
    }

    @Override
    public List<Order> getDateHistory(LocalDate startDate, LocalDate endDate) {
        OrderSpecification orderSpecification = new OrderSpecification(null, null, null, null, startDate, endDate, null, null, null);
        return orderRepository.findAll(orderSpecification);
    }

    @Override
    public List<Order> getSubTaskHistory(String subTaskName) throws SubTaskOperationException {
        SubTask subTask = subTaskService.findByName(subTaskName);
        OrderSpecification orderSpecification = new OrderSpecification(null, null, subTask, null, null, null, null, null, null);
        return orderRepository.findAll(orderSpecification);
    }

    @Override
    public List<Order> getTaskHistory(String taskName) throws TaskOperationException {
        Task task = taskService.findByName(taskName);
        OrderSpecification orderSpecification = new OrderSpecification(null, null, null, task, null, null, null, null, null);
        return orderRepository.findAll(orderSpecification);
    }

    @Override
    public List<Order> getOrderStatusHistory(OrderStatus orderStatus) {
        OrderSpecification orderSpecification = new OrderSpecification(null, null, null, null, null, null, orderStatus, null, null);
        return orderRepository.findAll(orderSpecification);
    }
}
