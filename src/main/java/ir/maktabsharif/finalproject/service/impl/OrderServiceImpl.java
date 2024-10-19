package ir.maktabsharif.finalproject.service.impl;


import ir.maktabsharif.finalproject.dto.CustomerDto;
import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.dto.SubTaskDto;
import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.entities.SubTask;
import ir.maktabsharif.finalproject.enumerations.OrderStatus;
import ir.maktabsharif.finalproject.exception.*;
import ir.maktabsharif.finalproject.repository.CustomerRepository;
import ir.maktabsharif.finalproject.repository.OrderRepository;
import ir.maktabsharif.finalproject.repository.SubTaskRepository;
import ir.maktabsharif.finalproject.service.OrderService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private MapperUtil mapperUtil;
    private final ValidationUtil validationUtil;
    private final CustomerRepository customerRepository;
    private final SubTaskRepository subTaskRepository;
    private final OrderRepository orderRepository;

    @Override
    public Order findByNameOfOrder(String nameOfOrder) {
        Order order = orderRepository.findByNameOfOrder(nameOfOrder);
        if (order != null) {
            return order;
        } else {
            throw new OrderNotFoundException("Order Not Found");
        }
    }

    @Override
    public void add(Order order) throws OrderOperationException {
        try {
//            if (validationUtil.isValid(order)) {
                orderRepository.save(order);
//            } else {
//                validationUtil.displayViolations(order);
//            }
        } catch (Exception e) {
            throw new OrderOperationException("An error occurred while adding an order ", e);
        }
    }

    @Override
    public void update(Order order) throws OrderOperationException {
        try {
            orderRepository.save(order);
        } catch (Exception e) {
            throw new OrderOperationException("An error occurred while updating an order ", e);
        }
    }

    @Override
    public void delete(Order order) throws OrderOperationException {
        try {
            orderRepository.delete(order);
        } catch (Exception e) {
            throw new OrderOperationException("An error occurred while deleting an order ", e);
        }
    }

    @Override
    public List<Order> findAll() throws OrderOperationException {
        try {
            return orderRepository.findAll();
        } catch (Exception e) {
            throw new OrderOperationException("An error occurred while finding all orders ", e);
        }
    }

    @Override
    public List<OrderDto> findByRelatedSubTask(SubTaskDto subTaskDto) throws OrderOperationException {
        try {
            SubTask subTask = subTaskRepository.findByName(subTaskDto.getSubTaskName());
            if (subTask != null) {
                return orderRepository.findByRelatedSubTask(subTask)
                        .stream()
                        .map(order -> mapperUtil.convertToDto(order))
                        .toList();
            } else {
                throw new SubTaskNotFoundException("SubTask Not Found");
            }
        } catch (Exception e) {
            throw new OrderOperationException("An error occured while finding related orders ", e);
        }
    }

    @Override
    public List<Order> findWaitingForSelectionOrders() throws OrderOperationException {
        try{
            return orderRepository.findOrderByOrderStatus(OrderStatus.WAITING_FOR_SPECIALIST_SELECTION);
        }catch (Exception e){
            throw new OrderOperationException("An error occured while finding related orders ",e);
        }
    }

    @Override
    public List<OrderDto> findCustomerOrders(CustomerDto customerDto) {
        Customer customer = customerRepository.findCustomerByFirstNameAndLastName(customerDto.getFirstname(), customerDto.getLastname());
        if (customer != null) {
            List<Order> customerOrders = orderRepository.findByCustomer(customer);
            if (customerOrders != null && !customerOrders.isEmpty()) {
                List<OrderDto> orderDtos = new ArrayList<>();
                for (Order order : customerOrders) {
                    orderDtos.add(mapperUtil.convertToDto(order));
                }
                return orderDtos;
            } else {
                throw new CustomerOrderListEmpty("Customer does not have any order ! ");
            }
        } else {
            throw new CustomerNotFoundException("Customer Not Found !");
        }
    }
}
