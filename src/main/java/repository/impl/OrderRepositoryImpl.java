package repository.impl;

import entities.Customer;
import entities.Order;
import enumerations.OrderStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import repository.OrderRepository;

import java.util.List;

public class OrderRepositoryImpl<T extends Order> extends BaseEntityRepositoryImpl<Order> implements OrderRepository {
    private final EntityManager em;

    public OrderRepositoryImpl(EntityManager em) {
        super(em);
        this.em = em;
    }

    @Override
    public Order findById(int orderId) {
        return em.find(Order.class, orderId);
    }

    @Override
    public List<Order> findAll() {
        return em.createQuery("SELECT o FROM Order o", Order.class).getResultList();
    }

    @Override
    public List<Order> findByOrderStatusAndCustomer(OrderStatus orderStatus, int customerId) {
        TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o WHERE o.status = :orderStatus AND o.customer.id = :customerId",Order.class);
        query.setParameter("orderStatus", orderStatus);
        query.setParameter("customerId", customerId);
        return query.getResultList();
    }
}
