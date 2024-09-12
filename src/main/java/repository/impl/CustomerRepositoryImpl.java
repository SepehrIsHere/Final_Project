package repository.impl;

import entities.Customer;
import entities.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import repository.CustomerRepository;

import java.util.List;

public class CustomerRepositoryImpl<T extends Customer> extends BaseEntityRepositoryImpl<Customer> implements CustomerRepository {
    private final EntityManager em;

    public CustomerRepositoryImpl(EntityManager em) {
        super(em);
        this.em = em;
    }

    @Override
    public Customer findById(int id) {
        return em.find(Customer.class, id);
    }

    @Override
    public List<Customer> findAll() {
        return em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
    }
}
