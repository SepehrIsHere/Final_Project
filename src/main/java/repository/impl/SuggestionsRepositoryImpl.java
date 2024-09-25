package repository.impl;

import entities.BaseEntity;
import entities.Order;
import entities.Suggestions;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import repository.SuggestionsRepository;

import java.util.List;

public class SuggestionsRepositoryImpl<T extends Suggestions> extends BaseEntityRepositoryImpl<Suggestions> implements SuggestionsRepository {
    private final EntityManager em;

    public SuggestionsRepositoryImpl(EntityManager em) {
        super(em);
        this.em = em;
    }

    @Override
    public List<Suggestions> findAll() {
        return em.createQuery("SELECT s FROM Suggestions s", Suggestions.class).getResultList();
    }

    @Override
    public Suggestions findById(int id) {
        return em.find(Suggestions.class, id);
    }

    @Override
    public List<Suggestions> findByOrder(Order order) {
        TypedQuery<Suggestions> query = em.createQuery("SELECT s FROM Suggestions s WHERE s.order = :order", Suggestions.class);
        query.setParameter("order", order);
        return query.getResultList();
    }

}
