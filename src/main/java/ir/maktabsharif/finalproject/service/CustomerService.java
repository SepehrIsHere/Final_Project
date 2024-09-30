package ir.maktabsharif.finalproject.service;


import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.exception.CustomerOperationException;

import java.util.List;

public interface CustomerService {
    void add(Customer customer) throws CustomerOperationException;

    void update(Customer customer) throws CustomerOperationException;

    void delete(Customer customer) throws CustomerOperationException;

    List<Customer> findAll() throws CustomerOperationException;

    Customer findById(int id) throws CustomerOperationException;

    Customer findByFirstNameAndLastName(String firstName, String lastName) throws CustomerOperationException;

}
