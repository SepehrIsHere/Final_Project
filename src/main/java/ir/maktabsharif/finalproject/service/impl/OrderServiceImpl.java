package ir.maktabsharif.finalproject.service.impl;


import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.enumerations.OrderStatus;
import ir.maktabsharif.finalproject.exception.OrderOperationException;
import ir.maktabsharif.finalproject.repository.CustomerRepository;
import ir.maktabsharif.finalproject.repository.OrderRepository;
import ir.maktabsharif.finalproject.service.OrderService;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final ValidationUtil validationUtil;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(ValidationUtil validationUtil, OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.validationUtil = validationUtil;
        this.orderRepository = orderRepository;
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

    @Override
    public Order findById(int id) throws OrderOperationException {
        try {
            return orderRepository.findByCustomerId(id);
        } catch (Exception e) {
            throw new OrderOperationException("An error occurred while finding an order by id ", e);
        }
    }

    @Override
    public List<Order> findByOrderStatusAndCustomer(Customer customer,OrderStatus orderStatus) throws OrderOperationException {
        try {
            return orderRepository.findByCustomerAndOrderStatus(customer,orderStatus);
        } catch (Exception e) {
            throw new OrderOperationException("An error occurred while finding an order by status and id ", e);
        }
    }
}
