package ir.maktabsharif.finalproject.service.impl;

import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.entities.Specialist;
import ir.maktabsharif.finalproject.entities.Users;
import ir.maktabsharif.finalproject.enumerations.Role;
import ir.maktabsharif.finalproject.exception.CustomerOperationException;
import ir.maktabsharif.finalproject.exception.NotFoundByFilterException;
import ir.maktabsharif.finalproject.exception.SpecialistOperationException;
import ir.maktabsharif.finalproject.exception.UserOperationException;
import ir.maktabsharif.finalproject.repository.CustomerRepository;
import ir.maktabsharif.finalproject.repository.SpecialistRepository;
import ir.maktabsharif.finalproject.repository.UsersRepository;
import ir.maktabsharif.finalproject.service.AdminDisplayService;
import ir.maktabsharif.finalproject.specification.CustomerSpecification;
import ir.maktabsharif.finalproject.specification.SpecialistSpecification;
import ir.maktabsharif.finalproject.specification.UsersSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminDisplayServiceImpl implements AdminDisplayService {
    @Autowired
    private final UsersRepository usersRepository;
    @Autowired
    private final CustomerRepository customerRepository;
    @Autowired
    private final SpecialistRepository specialistRepository;

    @Override
    public List<Users> searchUsers(String firstName, String lastName, String email, String username, String password, Role role, String orderBy, Boolean ascending) {
        try {
            Specification<Users> specification = new UsersSpecification(firstName, lastName, username, password, email, role, orderBy, ascending);
            return usersRepository.findAll(specification);
        } catch (Exception e) {
            throw new NotFoundByFilterException("User not found using custom filters ");
        }
    }

    @Override
    public List<Customer> searchCustomers(String firstName, String lastName, String email, String username, double credit, String orderBy, boolean ascending) {
        try {
            Specification<Customer> specification = new CustomerSpecification(firstName, lastName, email, username, credit, orderBy, ascending);
            return customerRepository.findAll(specification);
        } catch (Exception e) {
            throw new NotFoundByFilterException("Customer not found using custom filters ");
        }
    }

    @Override
    public List<Specialist> searchSpecialists(String firstName, String lastName, String email, String username, double score, String subTaskName, String orderBy, boolean ascending) {
        try {
            Specification<Specialist> specification = new SpecialistSpecification(firstName, lastName, email, username, score, subTaskName, orderBy, ascending);
            return specialistRepository.findAll(specification);
        } catch (Exception e) {
            throw new NotFoundByFilterException("Special ist not found using custom filters ");
        }
    }

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
