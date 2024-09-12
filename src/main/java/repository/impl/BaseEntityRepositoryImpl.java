package repository.impl;

import entities.BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import repository.BaseEntityRepository;

public class BaseEntityRepositoryImpl<T extends BaseEntity> implements BaseEntityRepository<T> {
    private final EntityManager em;

    public BaseEntityRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void add(T t) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(t);
        transaction.commit();
    }

    @Override
    public void update(T t) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(t);
        transaction.commit();
    }

    @Override
    public void delete(T t) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(t);
        transaction.commit();
    }
}
