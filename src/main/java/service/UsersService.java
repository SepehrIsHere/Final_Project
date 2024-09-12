package service;

import entities.Task;
import entities.Users;

import java.util.List;

public interface UsersService {
    void add(Users users);

    void update(Users users);

    void delete(Users users);

    List<Users> findAll();

    Users findById(int id);
}
