package service;

import entities.Comment;
import entities.Customer;

import java.util.List;

public interface CustomerService {
    void add(Customer customer);

    void update(Customer customer);

    void delete(Customer customer);

    List<Customer> findAll();

    Customer findById(int id);

    Customer findByFirstNameAndLastName(String firstName, String lastName);

}
