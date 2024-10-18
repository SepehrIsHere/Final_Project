package ir.maktabsharif.finalproject.service;

import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.entities.Specialist;
import ir.maktabsharif.finalproject.entities.Users;
import ir.maktabsharif.finalproject.enumerations.Role;
import ir.maktabsharif.finalproject.exception.CustomerOperationException;
import ir.maktabsharif.finalproject.exception.SpecialistOperationException;
import ir.maktabsharif.finalproject.exception.UserOperationException;

import java.util.List;

public interface AdminDisplayService {
    List<Customer> displayByCreditASC() throws CustomerOperationException;

    List<Customer> displayByCreditDESC() throws CustomerOperationException;

    List<Users> findByRole(Role role) throws UserOperationException;

    List<Users> findAllUserByEmailAsc() throws UserOperationException;

    List<Users> findAllUserByEmailDesc() throws UserOperationException;

    List<Users> findAllUserByFirstNameAsc() throws UserOperationException;

    List<Users> findAllUserByLastNameAsc() throws UserOperationException;

    List<Specialist> displayByScoreASC() throws SpecialistOperationException;

    List<Specialist> displayByScoreDESC() throws SpecialistOperationException;


}
