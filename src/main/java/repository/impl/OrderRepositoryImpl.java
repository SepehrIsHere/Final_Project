package repository.impl;

import entities.Order;
import jakarta.persistence.EntityManager;
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
}
