package repository;

import entities.Users;

import java.util.List;

public interface UsersRepository extends BaseEntityRepository<Users> {
    Users findById(int id);

    List<Users> findAll();
}
