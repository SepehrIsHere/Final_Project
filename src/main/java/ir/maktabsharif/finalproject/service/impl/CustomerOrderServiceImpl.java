package ir.maktabsharif.finalproject.service.impl;

import ir.maktabsharif.finalproject.dto.CustomerDto;
import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.dto.SubTaskDto;
import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.entities.SubTask;
import ir.maktabsharif.finalproject.enumerations.OrderStatus;
import ir.maktabsharif.finalproject.exception.*;
import ir.maktabsharif.finalproject.repository.CustomerRepository;
import ir.maktabsharif.finalproject.repository.OrderRepository;
import ir.maktabsharif.finalproject.repository.SubTaskRepository;
import ir.maktabsharif.finalproject.service.CustomerOrderService;
import ir.maktabsharif.finalproject.service.OrderService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerOrderServiceImpl implements CustomerOrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderService orderService;
    private final SubTaskRepository subTaskRepository;
    private MapperUtil mapperUtil;

    @Override
    public void placeAnOrder(CustomerDto customerDto, SubTaskDto subTaskDto, String nameOfOrder, LocalDate dateOfService, double suggestedPrice) throws OrderOperationException {
        Customer customer = findCustomer(customerDto);
        if (customer != null) {
            SubTask subTask = findSubTask(subTaskDto);
            if (subTask != null) {
                Order order = Order.builder()
                        .nameOfOrder(nameOfOrder)
                        .suggestedPrice(suggestedPrice)
                        .dateOfService(dateOfService)
                        .customer(customer)
                        .subTask(subTask)
                        .build();
                orderService.add(order);
            } else {
                throw new SubTaskNotFoundException("SubTask Not Found ! ");
            }
        } else {
            throw new CustomerNotFoundException("Customer Not Found ! ");
        }
    }

    @Override
    public void removeAnOrder(OrderDto orderDto) throws OrderOperationException {
        Order order = findOrder(orderDto);
        if (order != null) {
            orderService.delete(order);
        } else {
            throw new OrderNotFoundException("Order Not Found ! ");
        }
    }

    @Override
    public void changeOrderStatus(OrderDto orderDto, OrderStatus orderStatus) throws OrderOperationException {
        Order order = findOrder(orderDto);
        if (order != null) {
            order.setStatus(orderStatus);
            orderService.update(order);
        } else {
            throw new OrderNotFoundException("Order Not Found ! ");
        }
    }

    @Override
    public List<OrderDto> findCustomersOrders(CustomerDto customerDto) {
        Customer customer = findCustomer(customerDto);
        if (customer != null) {
            List<Order> customerOrders = customer.getOrders();
            if (customerOrders != null && !customerOrders.isEmpty()) {
                List<OrderDto> orderDtos = new ArrayList<>();
                for (Order order : customerOrders) {
                    orderDtos.add(mapperUtil.convertToDto(order));
                }
                return orderDtos;
            } else {
                throw new CustomerListNotFound("Customer's list of orders is nul or doesnt exist ");
            }
        } else {
            throw new CustomerNotFoundException("Customer Not Found ! ");
        }
    }

    private Customer findCustomer(CustomerDto customerDto) {
        return customerRepository.findCustomerByFirstNameAndLastName(customerDto.firstname(), customerDto.lastname());
    }

    private SubTask findSubTask(SubTaskDto subTaskDto) {
        return subTaskRepository.findByName(subTaskDto.subTaskName());
    }

    private Order findOrder(OrderDto orderDto) {
        return orderRepository.findByNameOfOrder(orderDto.nameOfOrder());
    }
}
