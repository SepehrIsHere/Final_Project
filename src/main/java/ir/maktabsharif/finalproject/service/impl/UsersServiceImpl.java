package ir.maktabsharif.finalproject.service.impl;


import ir.maktabsharif.finalproject.dto.UserDto;
import ir.maktabsharif.finalproject.entities.Users;
import ir.maktabsharif.finalproject.exception.UserOperationException;
import ir.maktabsharif.finalproject.repository.UsersRepository;
import ir.maktabsharif.finalproject.service.UsersService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final ValidationUtil validationUtil;
    private final UsersRepository usersRepository;
    private MapperUtil mapperUtil;

    @Override
    public void add(Users users) throws UserOperationException {
        try {
            if (validationUtil.isValid(users)) {
                usersRepository.save(users);
            } else {
                validationUtil.displayViolations(users);
            }
        } catch (Exception e) {
            throw new UserOperationException("An error occured while adding user", e);
        }
    }

    @Override
    public void update(Users users) throws UserOperationException {
        try {
            usersRepository.save(users);
        } catch (Exception e) {
            throw new UserOperationException("Error while updating user ", e);
        }
    }

    @Override
    public void delete(Users users) throws UserOperationException {
        try {
            usersRepository.delete(users);
        } catch (Exception e) {
            throw new UserOperationException("Error while deleting user ", e);
        }
    }

    @Override
    public List<Users> findAll() throws UserOperationException {
        try {
            return usersRepository.findAll();
        } catch (Exception e) {
            throw new UserOperationException("An error occured while finding user", e);
        }
    }

    @Override
    public Users findById(int id) throws UserOperationException {
        try {
            return usersRepository.findUserById(id);
        } catch (Exception e) {
            throw new UserOperationException("An error occured while finding user ", e);
        }
    }


}
