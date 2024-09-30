package ir.maktabsharif.finalproject.service.impl;


import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.exception.CustomerOperationException;
import ir.maktabsharif.finalproject.repository.CustomerRepository;
import ir.maktabsharif.finalproject.service.CustomerService;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final ValidationUtil validationUtil;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(ValidationUtil validationUtil, CustomerRepository customerRepository) {
        this.validationUtil = validationUtil;
        this.customerRepository = customerRepository;
    }

    @Override
    public void add(Customer customer) throws CustomerOperationException {
        try {
            if (validationUtil.isValid(customer)) {
                customerRepository.save(customer);
            } else {
                validationUtil.displayViolations(customer);
            }
        } catch (Exception e) {
            throw new CustomerOperationException("An error occured while adding customer", e);
        }
    }

    @Override
    public void update(Customer customer) throws CustomerOperationException {
        try {
            customerRepository.save(customer);
        } catch (Exception e) {
            throw new CustomerOperationException("An error occured while updating customer", e);
        }
    }

    @Override
    public void delete(Customer customer) throws CustomerOperationException {
        try {
            customerRepository.delete(customer);
        } catch (Exception e) {
            throw new CustomerOperationException("An error occured while deleting customer", e);
        }
    }

    @Override
    public List<Customer> findAll() throws CustomerOperationException {
        try {
            return customerRepository.findAll();
        } catch (Exception e) {
            throw new CustomerOperationException("An error occured while finding all customers", e);
        }
    }

    @Override
    public Customer findById(int id) throws CustomerOperationException {
        try {
            return customerRepository.findCustomerById(id);
        } catch (Exception e) {
            throw new CustomerOperationException("An error occured while finding customer", e);
        }
    }

    @Override
    public Customer findByFirstNameAndLastName(String firstName, String lastName) throws CustomerOperationException {
        try {
            return customerRepository.findCustomerByFirstNameAndLastName(firstName, lastName);
        } catch (Exception e) {
            throw new CustomerOperationException("An error occured while finding customer", e);
        }
    }
}
