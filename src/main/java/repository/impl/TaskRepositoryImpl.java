package repository.impl;

import entities.Task;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import repository.TaskRepository;

import java.util.List;

public class TaskRepositoryImpl<T extends Task> extends BaseEntityRepositoryImpl<Task> implements TaskRepository {
    private final EntityManager em;

    public TaskRepositoryImpl(EntityManager em) {
        super(em);
        this.em = em;
    }

    @Override
    public Task findById(int id) {
        return em.find(Task.class, id);
    }

    @Override
    public List<Task> findAll() {
        return em.createQuery("SELECT t FROM Task t", Task.class).getResultList();
    }

    @Override
    public Task findByName(String name) {
        TypedQuery<Task> query = em.createQuery("SELECT t FROM Task t WHERE t.taskName = :name", Task.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }
}
