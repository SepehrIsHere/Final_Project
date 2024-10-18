package ir.maktabsharif.finalproject.service.impl;

import ir.maktabsharif.finalproject.dto.CustomerDto;
import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.dto.SubTaskDto;
import ir.maktabsharif.finalproject.dto.SuggestionDto;
import ir.maktabsharif.finalproject.entities.*;
import ir.maktabsharif.finalproject.enumerations.OrderStatus;
import ir.maktabsharif.finalproject.exception.*;
import ir.maktabsharif.finalproject.repository.CustomerRepository;
import ir.maktabsharif.finalproject.repository.OrderRepository;
import ir.maktabsharif.finalproject.repository.SubTaskRepository;
import ir.maktabsharif.finalproject.service.*;
import ir.maktabsharif.finalproject.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerOrderServiceImpl implements CustomerOrderService {
    private final ReceiptService receiptService;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final SuggestionsService suggestionsService;
    private final OrderService orderService;
    private final SpecialistService specialistService;
    private final SubTaskRepository subTaskRepository;
    private final MapperUtil mapperUtil;

    @Override
    public OrderDto placeAnOrder(OrderDto orderDto) throws OrderOperationException {
        Customer customer = customerRepository.findCustomerByFirstNameAndLastName(orderDto.getCustomerFirstName(), orderDto.getCustomerLastName());
        if (customer != null) {
            SubTask subTask = subTaskRepository.findByName(orderDto.getSubTaskName());
            if (subTask != null) {
                Order order = Order.builder()
                        .nameOfOrder(orderDto.getNameOfOrder())
                        .suggestedPrice(orderDto.getSuggestedPrice())
                        .dateOfService(orderDto.getDateOfService())
                        .description(orderDto.getDescription())
                        .customer(customer)
                        .status(OrderStatus.WAITING_FOR_SPECIALIST_SELECTION)
                        .subTask(subTask)
                        .build();
                orderService.add(order);
                return orderDto;
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
    public List<OrderDto> findCustomersOrders(String customerFirstName,String customerLastName) throws CustomerOperationException {
        Customer customer = customerService.findByFirstNameAndLastName(customerFirstName,customerLastName);
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

    @Override
    public void changeOrderStatusToStarted(String nameOfOrder,String specialistFirstName,String specialistLastName) throws OrderOperationException {
        try {
            Order order = orderService.findByNameOfOrder(nameOfOrder);
            Suggestions suggestions = suggestionsService.findSuggestionsByNameOfOrderAndSpecialist(nameOfOrder, specialistFirstName, specialistLastName);
            Specialist specialist = specialistService.findByFirstNameAndLastName(specialistFirstName,specialistLastName);
            if (order != null) {
                if (suggestions != null) {
                    if (LocalDate.now().isAfter(suggestions.getSuggestedDate())) {
                        order.setStatus(OrderStatus.STARTED);
                        order.setSpecialist(specialist);
                        orderService.update(order);
                        Receipt receipt = Receipt.builder()
                                .totalAmount(order.getSuggestedPrice())
                                .specialistFirstName(order.getSpecialist().getFirstName())
                                .specialistLastName(order.getSpecialist().getLastName())
                                .dateOfService(suggestions.getSuggestedDate())
                                .timeOfService(suggestions.getWorkTime())
                                .nameOfOrder(order.getNameOfOrder())
                                .customerFirstName(order.getCustomer().getFirstName())
                                .customerLastName(order.getCustomer().getLastName())
                                .build();
                        receiptService.add(receipt);
                    } else {
                        throw new InvalidDateException("You can change to start after the date not before");
                    }
                } else {
                    throw new SuggestionNotFound("Suggestion Not Found ! ");
                }
            } else {
                throw new OrderNotFoundException("Order Not Found ! ");
            }
        } catch (Exception e) {
            throw new OrderOperationException("An error occured while trying to change order status to started", e);
        }
    }

    @Override
    public void changeOrderStatusToFinished(String nameOfOrder) throws OrderOperationException {
        Order order = orderService.findByNameOfOrder(nameOfOrder);
        if (order != null) {
            if (order.getStatus().equals(OrderStatus.STARTED)) {
                order.setStatus(OrderStatus.COMPLETED);
                orderService.update(order);
            } else {
                throw new OrderStatusException("Order Status is invalid !");
            }
        } else {
            throw new OrderNotFoundException("Order Not Found ! ");
        }
    }

    private Order findOrder(OrderDto orderDto) {
        return orderService.findByNameOfOrder(orderDto.getNameOfOrder());
    }
}
