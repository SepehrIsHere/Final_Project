package service;

import entities.Order;
import entities.Specialist;

import java.util.List;

public interface SpecialistService {
    void add(Specialist specialist);

    void update(Specialist specialist);

    void delete(Specialist specialist);

    List<Specialist> findAll();

    Specialist findById(int id);
}
