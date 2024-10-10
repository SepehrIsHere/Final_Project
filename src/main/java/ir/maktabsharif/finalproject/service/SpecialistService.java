package ir.maktabsharif.finalproject.service;


import ir.maktabsharif.finalproject.dto.SpecialistDto;
import ir.maktabsharif.finalproject.dto.SubTaskDto;
import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.entities.Specialist;
import ir.maktabsharif.finalproject.entities.Suggestions;
import ir.maktabsharif.finalproject.exception.SpecialistOperationException;
import ir.maktabsharif.finalproject.exception.SubTaskOperationException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface SpecialistService {
    SpecialistDto signUp(String firstName,String lastName,String email,String username,String password) throws SpecialistOperationException;

    void add(Specialist specialist) throws SpecialistOperationException;

    void update(Specialist specialist) throws SpecialistOperationException;

    void delete(SpecialistDto specialistDto) throws SpecialistOperationException;

    List<Specialist> findAll() throws SpecialistOperationException;

    Specialist findById(int id) throws SpecialistOperationException;

    Specialist findByFirstNameAndLastName(String firstName, String lastName) throws SpecialistOperationException;

    boolean checkSpecialistImage(SpecialistDto specialistDto) throws SpecialistOperationException;

    boolean doesSpecialistExist(SpecialistDto specialistDto) throws SpecialistOperationException;

}
