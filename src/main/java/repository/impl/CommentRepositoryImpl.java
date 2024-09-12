package repository.impl;

import entities.Comment;
import jakarta.persistence.EntityManager;
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
}
