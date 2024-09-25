package repository.impl;

import entities.Comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import repository.CommentRepository;

import java.util.List;

public class CommentRepositoryImpl<T extends Comment> extends BaseEntityRepositoryImpl<Comment> implements CommentRepository {
    private final EntityManager em;

    public CommentRepositoryImpl(EntityManager em) {
        super(em);
        this.em = em;
    }

    @Override
    public Comment findById(int id) {
        return em.find(Comment.class, id);
    }

    @Override
    public List<Comment> findAll() {
        return em.createQuery("SELECT c FROM Comment c", Comment.class).getResultList();
    }

    @Override
    public List<Comment> findByCustomer(int customerId) {
        TypedQuery<Comment> query = em.createQuery("SELECT c FROM Comment c WHERE c.customer.id = :customerId", Comment.class);
        query.setParameter("customerId", customerId);
        return query.getResultList();
    }
}
