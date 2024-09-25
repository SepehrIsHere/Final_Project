package repository.impl;

import entities.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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

    @Override
    public Customer findByFirstNameAndLastName(String firstName, String lastName) {
        TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c where c.userDetails.firstName = :firstName and c.userDetails.lastName = :lastName", Customer.class);
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        return query.getSingleResult();
    }
}
