package ir.maktabsharif.finalproject.service;

import ir.maktabsharif.finalproject.dto.CustomerDto;
import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.entities.Order;

import java.util.List;

public interface CustomerEditService {
    void changeUserName(CustomerDto customerDto, String newName);

    void changePassword(CustomerDto customerDto, String newPassword);

    void changeFirstName(CustomerDto customerDto, String newFirstName);

    void changeLastName(CustomerDto customerDto, String newLastName);

    List<OrderDto> findCustomerOrders(CustomerDto customerDto);

}
