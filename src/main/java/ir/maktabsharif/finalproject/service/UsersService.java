package ir.maktabsharif.finalproject.service;



import ir.maktabsharif.finalproject.entities.Users;
import ir.maktabsharif.finalproject.exception.UserOperationException;

import java.util.List;

public interface UsersService {
    void add(Users users) throws UserOperationException;

    void update(Users users) throws UserOperationException;

    void delete(Users users) throws UserOperationException;

    List<Users> findAll() throws UserOperationException;

    Users findById(int id) throws UserOperationException;

    Users login(String username, String password) throws UserOperationException;
}
