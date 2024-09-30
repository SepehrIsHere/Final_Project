package ir.maktabsharif.finalproject.service;


import ir.maktabsharif.finalproject.entities.Specialist;
import ir.maktabsharif.finalproject.exception.SpecialistOperationException;

import java.util.List;

public interface SpecialistService {
    void add(Specialist specialist) throws SpecialistOperationException;

    void update(Specialist specialist) throws SpecialistOperationException;

    void delete(Specialist specialist) throws SpecialistOperationException;

    List<Specialist> findAll() throws SpecialistOperationException;

    Specialist findById(int id) throws SpecialistOperationException;

    Specialist findByFirstNameAndLastName(String firstName, String lastName) throws SpecialistOperationException;

    boolean checkSpecialistImage(Specialist specialist) throws SpecialistOperationException;
}
