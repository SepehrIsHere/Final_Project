package ir.maktabsharif.finalproject.service.impl;

import ir.maktabsharif.finalproject.entities.Users;
import ir.maktabsharif.finalproject.exception.InvalidFieldValueException;
import ir.maktabsharif.finalproject.exception.UserNotFoundException;
import ir.maktabsharif.finalproject.exception.UserOperationException;
import ir.maktabsharif.finalproject.repository.UsersRepository;
import ir.maktabsharif.finalproject.service.UserEditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEditServiceImpl implements UserEditService {
    private final UsersRepository usersRepository;

    @Override
    public void changeFirstName(String firstName, String lastName, String newFirstName) throws UserOperationException {
        try {
            Users user = usersRepository.findByFirstNameAndLastName(firstName, lastName);
            if (user != null) {
                user.setFirstName(newFirstName);
                usersRepository.save(user);
            } else {
                throw new UserNotFoundException("User Not Found !");
            }
        } catch (Exception e) {
            throw new UserOperationException("An error occured while updating firstname ", e);
        }
    }

    @Override
    public void changeLastName(String firstName, String lastName, String newLastName) throws UserOperationException {
        try {
            Users user = usersRepository.findByFirstNameAndLastName(firstName, lastName);
            if (user != null) {
                user.setLastName(newLastName);
                usersRepository.save(user);
            } else {
                throw new UserNotFoundException("User Not Found !");
            }
        } catch (Exception e) {
            throw new UserOperationException("An error occured while updating lastname ", e);
        }
    }

    @Override
    public void changeUsername(String firstName, String lastName, String newUserName) throws UserOperationException {
        try {
            Users user = usersRepository.findByFirstNameAndLastName(firstName, lastName);
            if (user != null) {
                user.setUsername(newUserName);
                usersRepository.save(user);
            } else {
                throw new UserNotFoundException("User Not Found !");
            }
        } catch (Exception e) {
            throw new UserOperationException("An error occured while updating username ", e);
        }
    }

    @Override
    public void changePassword(String firstName, String lastName, String newPassword) throws UserOperationException {
        try {
            Users user = usersRepository.findByFirstNameAndLastName(firstName, lastName);
            if (user != null) {
                if (newPassword.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$")) {
                    user.setPassword(newPassword);
                    usersRepository.save(user);
                } else {
                    throw new InvalidFieldValueException("password is not valid");
                }
            } else {
                throw new UserNotFoundException("User Not Found !");
            }
        } catch (Exception e) {
            throw new UserOperationException("An error occured while updating password ", e);
        }
    }

    @Override
    public void changeEmail(String firstName, String lastName, String newEmail) throws UserOperationException {
        try {
            Users user = usersRepository.findByFirstNameAndLastName(firstName, lastName);
            if (user != null) {
                user.setEmail(newEmail);
                usersRepository.save(user);
            } else {
                throw new UserNotFoundException("User Not Found !");
            }
        } catch (Exception e) {
            throw new UserOperationException("An error occured while updating email ", e);
        }
    }
}
