package repository;

import entities.Customer;

import java.util.List;

public interface CustomerRepository extends BaseEntityRepository<Customer> {
    Customer findById(int id);

    List<Customer> findAll();

    Customer findByFirstNameAndLastName(String firstName, String lastName);
}
