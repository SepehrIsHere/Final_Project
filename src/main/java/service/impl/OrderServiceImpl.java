package service.impl;

import entities.Order;
import enumerations.OrderStatus;
import repository.OrderRepository;
import service.OrderService;
import util.ValidationUtil;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void add(Order order) {
        try {
            orderRepository.add(order);
        } catch (Exception e) {
            System.out.println("An error occured while adding an order" + e.getMessage());
        }
    }

    @Override
    public void update(Order order) {
        try {
            orderRepository.update(order);
        } catch (Exception e) {
            System.out.println("An error occured while updating an order" + e.getMessage());
        }
    }

    @Override
    public void delete(Order order) {
        try {
            orderRepository.delete(order);
        } catch (Exception e) {
            System.out.println("An error occured while deleting an order" + e.getMessage());
        }
    }

    @Override
    public List<Order> findAll() {
        try {
            return orderRepository.findAll();
        } catch (Exception e) {
            System.out.println("An error occured while finding all orders");
        }
        return null;
    }

    @Override
    public Order findById(int id) {
        try {
            return orderRepository.findById(id);
        } catch (Exception e) {
            System.out.println("An error occured while finding an order" + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Order> findByOrderStatusAndCustomer(OrderStatus orderStatus, int customerId) {
        try {
            orderRepository.findByOrderStatusAndCustomer(orderStatus, customerId);
        } catch (Exception e) {
            System.out.println("An error occured while finding an order" + e.getMessage());
        }
        return null;
    }
}
