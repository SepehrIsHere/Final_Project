package repository.impl;

import entities.BaseEntity;
import entities.Suggestions;
import jakarta.persistence.EntityManager;
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
}
