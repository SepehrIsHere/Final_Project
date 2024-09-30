package repository.impl;

import entities.Specialist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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

    @Override
    public Specialist findByFirstNameAndLastName(String firstName, String lastName) {
        TypedQuery<Specialist> query = em.createQuery("SELECT s FROM Specialist s WHERE s.userDetails.firstName = :firstName AND s.userDetails.lastName = :lastName", Specialist.class);
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        return query.getSingleResult();
    }
}
