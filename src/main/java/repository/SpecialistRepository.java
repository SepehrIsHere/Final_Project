package repository;

import entities.Specialist;

import java.util.List;

public interface SpecialistRepository extends BaseEntityRepository<Specialist> {
    Specialist findById(int id);

    List<Specialist> findAll();
}
