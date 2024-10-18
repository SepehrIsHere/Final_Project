package ir.maktabsharif.finalproject.service;

import ir.maktabsharif.finalproject.dto.CustomerDto;
import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.dto.SubTaskDto;
import ir.maktabsharif.finalproject.dto.SuggestionDto;
import ir.maktabsharif.finalproject.entities.Suggestions;
import ir.maktabsharif.finalproject.enumerations.OrderStatus;
import ir.maktabsharif.finalproject.exception.CustomerOperationException;
import ir.maktabsharif.finalproject.exception.OrderOperationException;

import java.time.LocalDate;
import java.util.List;

public interface CustomerOrderService {
    OrderDto placeAnOrder(OrderDto orderDto) throws OrderOperationException;

    void removeAnOrder(OrderDto orderDto) throws OrderOperationException;

    List<OrderDto> findCustomersOrders(String customerFirstName, String customerLastName) throws CustomerOperationException;

    void changeOrderStatusToStarted(String nameOfOrder,String specialistFirstName,String specialistLastName) throws OrderOperationException;

    void changeOrderStatusToFinished(String nameOfOrder) throws OrderOperationException;
}
