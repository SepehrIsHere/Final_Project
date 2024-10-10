package ir.maktabsharif.finalproject.service;

import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.exception.OrderOperationException;

import java.util.List;

public interface OrderService {

    void add(Order order) throws OrderOperationException;

    void update(Order order) throws OrderOperationException;

    void delete(Order order) throws OrderOperationException;

    List<Order> findAll() throws OrderOperationException;

    Order findByNameOfOrder(String nameOfOrder);
}
