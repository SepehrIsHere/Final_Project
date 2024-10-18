package ir.maktabsharif.finalproject.service;

import ir.maktabsharif.finalproject.dto.CustomerDto;
import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.dto.SubTaskDto;
import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.entities.SubTask;
import ir.maktabsharif.finalproject.enumerations.OrderStatus;
import ir.maktabsharif.finalproject.exception.OrderOperationException;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderService {

    void add(Order order) throws OrderOperationException;

    void update(Order order) throws OrderOperationException;

    void delete(Order order) throws OrderOperationException;

    List<Order> findAll() throws OrderOperationException;

    Order findByNameOfOrder(String nameOfOrder);

    List<OrderDto> findByRelatedSubTask(SubTaskDto subTaskDto) throws OrderOperationException;

    List<Order> findWaitingForSelectionOrders() throws OrderOperationException;

    List<OrderDto> findCustomerOrders(CustomerDto customerDto);

}
