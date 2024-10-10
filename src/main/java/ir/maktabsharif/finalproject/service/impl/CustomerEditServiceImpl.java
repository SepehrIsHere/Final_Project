package ir.maktabsharif.finalproject.service.impl;

import ir.maktabsharif.finalproject.dto.CustomerDto;
import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.exception.CustomerNotFoundException;
import ir.maktabsharif.finalproject.exception.CustomerOrderListEmpty;
import ir.maktabsharif.finalproject.repository.CustomerRepository;
import ir.maktabsharif.finalproject.repository.OrderRepository;
import ir.maktabsharif.finalproject.service.CustomerEditService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerEditServiceImpl implements CustomerEditService {
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private MapperUtil mapperUtil;

    @Override
    public void changeUserName(CustomerDto customerDto, String newName) {
        Customer customer = customerRepository.findCustomerByFirstNameAndLastName(customerDto.firstname(), customerDto.lastname());
        if (customer != null) {
            customer.setFirstName(newName);
            customerRepository.save(customer);
        } else {
            throw new CustomerNotFoundException("Customer Not Found !");
        }

    }

    @Override
    public void changePassword(CustomerDto customerDto, String newPassword) {
        Customer customer = customerRepository.findCustomerByFirstNameAndLastName(customerDto.firstname(), customerDto.lastname());
        if (customer != null) {
            customer.setPassword(newPassword);
            customerRepository.save(customer);
        } else {
            throw new CustomerNotFoundException("Customer Not Found !");
        }
    }

    @Override
    public void changeFirstName(CustomerDto customerDto, String newFirstName) {
        Customer customer = customerRepository.findCustomerByFirstNameAndLastName(customerDto.firstname(), customerDto.lastname());
        if (customer != null) {
            customer.setFirstName(newFirstName);
            customerRepository.save(customer);
        } else {
            throw new CustomerNotFoundException("Customer Not Found !");
        }
    }

    @Override
    public void changeLastName(CustomerDto customerDto, String newLastName) {
        Customer customer = customerRepository.findCustomerByFirstNameAndLastName(customerDto.firstname(), customerDto.lastname());
        if (customer != null) {
            customer.setLastName(newLastName);
            customerRepository.save(customer);
        } else {
            throw new CustomerNotFoundException("Customer Not Found !");
        }
    }

    @Override
    public List<OrderDto> findCustomerOrders(CustomerDto customerDto) {
        Customer customer = customerRepository.findCustomerByFirstNameAndLastName(customerDto.firstname(), customerDto.lastname());
        if (customer != null) {
            List<Order> customerOrders = orderRepository.findByCustomer(customer);
            if (customerOrders != null && !customerOrders.isEmpty()) {
                List<OrderDto> orderDtos = new ArrayList<>();
                for (Order order : customerOrders) {
                    orderDtos.add(mapperUtil.convertToDto(order));
                }
                return orderDtos;
            } else {
                throw new CustomerOrderListEmpty("Customer does not have any order ! ");
            }
        } else {
            throw new CustomerNotFoundException("Customer Not Found !");
        }
    }
}
