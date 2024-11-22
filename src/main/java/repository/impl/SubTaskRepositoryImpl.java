package repository.impl;

import entities.SubTask;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import repository.SubTaskRepository;

import java.util.List;

public class SubTaskRepositoryImpl<T extends SubTask> extends BaseEntityRepositoryImpl<SubTask> implements SubTaskRepository {
    private final EntityManager em;

    public SubTaskRepositoryImpl(EntityManager em) {
        super(em);
        this.em = em;
    }

    @Override
    public SubTask findById(int id) {
        return em.find(SubTask.class, id);
    }

    @Override
    public List<SubTask> findAll() {
        return em.createQuery("SELECT s FROM SubTask s", SubTask.class).getResultList();
    }

    @Override
    public SubTask findByName(String subTaskName) {
        TypedQuery<SubTask> query = em.createQuery("SELECT s FROM SubTask s where s.name = :subTaskName", SubTask.class);
        query.setParameter("subTaskName", subTaskName);
        return query.getSingleResult();
    }
}
