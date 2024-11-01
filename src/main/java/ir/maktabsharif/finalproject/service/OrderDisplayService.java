package ir.maktabsharif.finalproject.service;

import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.enumerations.OrderStatus;
import ir.maktabsharif.finalproject.exception.CustomerOperationException;
import ir.maktabsharif.finalproject.exception.SpecialistOperationException;
import ir.maktabsharif.finalproject.exception.SubTaskOperationException;
import ir.maktabsharif.finalproject.exception.TaskOperationException;

import java.time.LocalDate;
import java.util.List;

public interface OrderDisplayService {
    List<Order> getCustomerHistory(String customerFirstName, String customerLastName) throws CustomerOperationException;

    List<Order> getSpecialistHistory(String specialistFirstName, String specialistLastName) throws SpecialistOperationException;

    List<Order> getDateHistory(LocalDate startDate, LocalDate endDate);

    List<Order> getSubTaskHistory(String subTaskName) throws SubTaskOperationException;

    List<Order> getTaskHistory(String taskName) throws TaskOperationException;

    List<Order> getOrderStatusHistory(OrderStatus orderStatus);
}
