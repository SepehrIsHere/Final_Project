package repository.impl;

import entities.Specialist;
import jakarta.persistence.EntityManager;
import repository.SpecialistRepository;

import java.util.List;

public class SpecialistRepositoryImpl<T extends Specialist> extends BaseEntityRepositoryImpl<Specialist> implements SpecialistRepository {
    private final EntityManager em;

    public SpecialistRepositoryImpl(EntityManager em) {
        super(em);
        this.em = em;
    }

    @Override
    public Specialist findById(int id) {
        return em.find(Specialist.class, id);
    }

    @Override
    public List<Specialist> findAll() {
        return em.createQuery("SELECT s FROM Specialist s", Specialist.class).getResultList();
    }
}