package repository.impl;

import entities.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import repository.UsersRepository;

import java.util.List;

public class UsersRepositoryImpl<T extends Users> extends BaseEntityRepositoryImpl<Users> implements UsersRepository {
    private final EntityManager em;

    public UsersRepositoryImpl(EntityManager em) {
        super(em);
        this.em = em;
    }

    @Override
    public Users findById(int id) {
        return em.find(Users.class, id);
    }

    @Override
    public List<Users> findAll() {
        return em.createQuery("SELECT u FROM Users u", Users.class).getResultList();
    }
}
