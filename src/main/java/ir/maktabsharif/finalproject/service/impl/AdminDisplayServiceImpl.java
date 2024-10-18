package ir.maktabsharif.finalproject.service.impl;

import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.entities.Specialist;
import ir.maktabsharif.finalproject.entities.Users;
import ir.maktabsharif.finalproject.enumerations.Role;
import ir.maktabsharif.finalproject.exception.CustomerOperationException;
import ir.maktabsharif.finalproject.exception.SpecialistOperationException;
import ir.maktabsharif.finalproject.exception.UserOperationException;
import ir.maktabsharif.finalproject.repository.CustomerRepository;
import ir.maktabsharif.finalproject.repository.SpecialistRepository;
import ir.maktabsharif.finalproject.repository.UsersRepository;
import ir.maktabsharif.finalproject.service.AdminDisplayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminDisplayServiceImpl implements AdminDisplayService {
    private final UsersRepository usersRepository;
    private final CustomerRepository customerRepository;
    private final SpecialistRepository specialistRepository;

    @Override
    public List<Customer> displayByCreditASC() throws CustomerOperationException {
        try {
            return customerRepository.displayByCreditASC();
        } catch (Exception e) {
            throw new CustomerOperationException("An error occured while listing customer by credit ASC", e);
        }
    }

    @Override
    public List<Customer> displayByCreditDESC() throws CustomerOperationException {
        try {
            return customerRepository.displayByCreditDESC();
        } catch (Exception e) {
            throw new CustomerOperationException("An error occured while listing customer by credit DESC", e);
        }
    }

    @Override
    public List<Users> findByRole(Role role) throws UserOperationException {
        try {
            return usersRepository.findByRole(role);
        } catch (Exception e) {
            throw new UserOperationException("An error occured while listing user by role", e);
        }
    }

    @Override
    public List<Users> findAllUserByEmailAsc() throws UserOperationException {
        try {
            return usersRepository.findAllOrderByEmailAsc();
        } catch (Exception e) {
            throw new UserOperationException("An error occured while listing user by email ASC", e);
        }
    }

    @Override
    public List<Users> findAllUserByEmailDesc() throws UserOperationException {
        try {
            return usersRepository.findAllOrderByEmailDesc();
        } catch (Exception e) {
            throw new UserOperationException("An error occured while listing user by email DESC", e);
        }
    }

    @Override
    public List<Users> findAllUserByFirstNameAsc() throws UserOperationException {
        try {
            return usersRepository.findAllOrderByFirstNameAsc();
        } catch (Exception e) {
            throw new UserOperationException("An error occured while listing user by firstname ASC", e);
        }
    }

    @Override
    public List<Users> findAllUserByLastNameAsc() throws UserOperationException {
        try {
            return usersRepository.findAllOrderByLastNameAsc();
        } catch (Exception e) {
            throw new UserOperationException("An error occured while listing user by lastname ASC", e);
        }
    }

    @Override
    public List<Specialist> displayByScoreASC() throws SpecialistOperationException {
        try {
            return specialistRepository.displayByCreditASC();
        } catch (Exception e) {
            throw new SpecialistOperationException("An error occured while listing specialist by credit ASC", e);
        }
    }

    @Override
    public List<Specialist> displayByScoreDESC() throws SpecialistOperationException {
        try {
            return specialistRepository.displayByCreditDESC();
        } catch (Exception e) {
            throw new SpecialistOperationException("An error occured while listing specialist by credit DESC", e);
        }
    }
}
