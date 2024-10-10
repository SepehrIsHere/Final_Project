package ir.maktabsharif.finalproject.service.impl;


import ir.maktabsharif.finalproject.dto.CustomerDto;
import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.dto.SubTaskDto;
import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.enumerations.OrderStatus;
import ir.maktabsharif.finalproject.exception.OrderNotFoundException;
import ir.maktabsharif.finalproject.exception.OrderOperationException;
import ir.maktabsharif.finalproject.repository.CustomerRepository;
import ir.maktabsharif.finalproject.repository.OrderRepository;
import ir.maktabsharif.finalproject.repository.SubTaskRepository;
import ir.maktabsharif.finalproject.service.OrderService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        if(order != null){
            return order;
        }else{
            throw new OrderNotFoundException("Order Not Found");
        }
    }

    @Override
    public void add(Order order) throws OrderOperationException {
        try {
            if (validationUtil.isValid(order)) {
                orderRepository.save(order);
            } else {
                validationUtil.displayViolations(order);
            }
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

}
