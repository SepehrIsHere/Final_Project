package service.impl;

import entities.Customer;
import repository.CustomerRepository;
import service.CustomerService;
import util.ValidationUtil;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void add(Customer customer) {
        try {
            customerRepository.add(customer);
        } catch (Exception e) {
            System.out.println("An error occured while adding customer" + e.getMessage());
        }
    }

    @Override
    public void update(Customer customer) {
        try {
            customerRepository.update(customer);
        } catch (Exception e) {
            System.out.println("An error occured while updating customer" + e.getMessage());
        }
    }

    @Override
    public void delete(Customer customer) {
        try {
            customerRepository.delete(customer);
        } catch (Exception e) {
            System.out.println("An error occured while deleting customer" + e.getMessage());
        }
    }

    @Override
    public List<Customer> findAll() {
        try {
            return customerRepository.findAll();
        } catch (Exception e) {
            System.out.println("An error occured while finding all customers");
        }
        return null;
    }

    @Override
    public Customer findById(int id) {
        try {
            return customerRepository.findById(id);
        } catch (Exception e) {
            System.out.println("An error occured while finding customer" + e.getMessage());
        }
        return null;
    }

    @Override
    public Customer findByFirstNameAndLastName(String firstName, String lastName) {
        try {
            return customerRepository.findByFirstNameAndLastName(firstName, lastName);
        } catch (Exception e) {
            System.out.println("An error occured while finding customer" + e.getMessage());
        }
        return null;
    }
}
