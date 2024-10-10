package ir.maktabsharif.finalproject.service;

import ir.maktabsharif.finalproject.dto.CustomerDto;
import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.dto.SubTaskDto;
import ir.maktabsharif.finalproject.enumerations.OrderStatus;
import ir.maktabsharif.finalproject.exception.OrderOperationException;

import java.time.LocalDate;
import java.util.List;

public interface CustomerOrderService {
    void placeAnOrder(CustomerDto customerDto, SubTaskDto subTaskDto, String nameOfOrder, LocalDate dateOfService, double suggestedPrice) throws OrderOperationException;

    void removeAnOrder(OrderDto orderDto) throws OrderOperationException;

    void changeOrderStatus(OrderDto orderDto, OrderStatus orderStatus) throws OrderOperationException;

    List<OrderDto> findCustomersOrders(CustomerDto customerDto);
}
