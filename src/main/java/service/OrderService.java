package service;

import entities.Customer;
import entities.Order;

import java.util.List;

public interface OrderService {
    void add(Order order);

    void update(Order order);

    void delete(Order order);

    List<Order> findAll();

    Order findById(int id);
}
