package service.impl;

import entities.Users;
import repository.UsersRepository;
import service.UsersService;
import util.ValidationUtil;

import java.util.List;

public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final ValidationUtil validationUtil;

    public UsersServiceImpl(UsersRepository usersRepository, ValidationUtil validationUtil) {
        this.usersRepository = usersRepository;
        this.validationUtil = validationUtil;
    }

    @Override
    public void add(Users users) {
        try {
            validationUtil.validation(users);
            usersRepository.add(users);
        } catch (Exception e) {
            System.out.println("An error occured while adding user" + e.getMessage());
        }
    }

    @Override
    public void update(Users users) {
        try {
            validationUtil.validation(users);
            usersRepository.update(users);
        } catch (Exception e) {
            System.out.println("An error occured while updating user" + e.getMessage());
        }
    }

    @Override
    public void delete(Users users) {
        try {
            usersRepository.delete(users);
        } catch (Exception e) {
            System.out.println("An error occured while deleting user" + e.getMessage());
        }
    }

    @Override
    public List<Users> findAll() {
        try {
            return usersRepository.findAll();
        } catch (Exception e) {
            System.out.println("An error occured while finding all users");
        }
        return null;
    }

    @Override
    public Users findById(int id) {
        try {
            return usersRepository.findById(id);
        } catch (Exception e) {
            System.out.println("An error occured while finding user with id " + id);
        }
        return null;
    }
}
