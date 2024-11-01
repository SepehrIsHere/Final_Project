package ir.maktabsharif.finalproject.service.impl;

import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.entities.*;
import ir.maktabsharif.finalproject.enumerations.OrderStatus;
import ir.maktabsharif.finalproject.enumerations.SpecialistStatus;
import ir.maktabsharif.finalproject.exception.*;
import ir.maktabsharif.finalproject.repository.CustomerRepository;
import ir.maktabsharif.finalproject.repository.OrderRepository;
import ir.maktabsharif.finalproject.repository.SubTaskRepository;
import ir.maktabsharif.finalproject.service.*;
import ir.maktabsharif.finalproject.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final OrderRepository orderRepository;
    private final SuggestionsService suggestionsService;
    private final OrderService orderService;
    private final SpecialistService specialistService;
    private final SubTaskRepository subTaskRepository;
    private final MapperUtil mapperUtil;
    private final Logger logger = LoggerFactory.getLogger(CustomerOrderServiceImpl.class);

    @Override
    public OrderDto placeAnOrder(OrderDto orderDto) {
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
                if(LocalDate.now().isBefore(order.getDateOfService())) {
                    throw new InvalidDateException("The date is invalid");
                }
                orderRepository.save(order);
                return orderDto;
            } else {
                throw new SubTaskNotFoundException("SubTask Not Found ! ");
            }
        } else {
            throw new CustomerNotFoundException("Customer Not Found ! ");
        }
    }

    @Override
    public List<OrderDto> findCustomersOrders(String customerFirstName, String customerLastName) throws CustomerOperationException {
        Customer customer = customerService.findByFirstNameAndLastName(customerFirstName, customerLastName);
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
    public void pickSpecialistForOrder(String specialistFirstName, String specialistLastName, String nameOfOrder) throws OrderOperationException {
        try {
            Order order = orderService.findByNameOfOrder(nameOfOrder);
            Specialist specialist = specialistService.findByFirstNameAndLastName(specialistFirstName, specialistLastName);
            Suggestions suggestions = suggestionsService.findSuggestionsByNameOfOrderAndSpecialist(nameOfOrder, specialistFirstName, specialistLastName);
            if (specialist.getSpecialistStatus().equals(SpecialistStatus.APPROVED)) {
                order.setSpecialist(specialist);
                order.setStatus(OrderStatus.WAITING_FOR_SPECIALIST_ARRIVAL);
                specialistService.update(specialist);
                logger.info("status before updating : {}", order.getStatus());
                orderService.update(order);
                suggestions.setOrder(order);
                logger.info("status after updating : {}", order.getStatus());
                suggestionsService.update(suggestions);
            } else {
                throw new InvalidSpecialistStatus("Specialist cant be set for order\nIt is not approved");
            }
        } catch (Exception e) {
            throw new OrderOperationException("An error occured while trying to pick specialist", e);
        }
    }

    @Override
    public void changeOrderStatusToStarted(String nameOfOrder, String specialistFirstName, String specialistLastName) throws OrderOperationException, SuggestionOperationException {
        try {
            Order order = orderService.findByNameOfOrder(nameOfOrder);
            Suggestions suggestions = suggestionsService.findSuggestionsByNameOfOrderAndSpecialist(nameOfOrder, specialistFirstName, specialistLastName);
            if (LocalDate.now().isBefore(suggestions.getSuggestedDate())) {
                throw new InvalidDateException("You can change to start after the date not before");
            }
            if (!order.getStatus().equals(OrderStatus.WAITING_FOR_SPECIALIST_ARRIVAL)) {
                throw new InvalidOrderStatus("Order must be in waiting for arrival so that it cant be started");
            }
            order.setStatus(OrderStatus.STARTED);
            orderService.update(order);
        } catch (Exception e) {
            throw new OrderOperationException("An error occured while trying to change order status to started" + e.getMessage(), e);
        }
    }

    @Override
    public void changeOrderStatusToFinished(String nameOfOrder) throws OrderOperationException {
        try{
            Order order = orderService.findByNameOfOrder(nameOfOrder);
            logger.info("status before updating to finished: {}", order.getStatus());
            if (!order.getStatus().equals(OrderStatus.STARTED)) {
                throw new InvalidOrderStatus("Order has not been started");
            }
            order.setStatus(OrderStatus.COMPLETED);
            orderService.update(order);
            logger.info("status after updating to finished: {}", order.getStatus());
        }catch (Exception e){
            throw new OrderOperationException("An error occured while trying to change order status to finished" + e.getMessage(), e);
        }
    }
}
