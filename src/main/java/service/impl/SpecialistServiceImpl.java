package service.impl;

import entities.Specialist;
import repository.SpecialistRepository;
import service.SpecialistService;
import util.ValidationUtil;

import java.util.List;

public class SpecialistServiceImpl implements SpecialistService {
    private final SpecialistRepository specialistRepository;

    public SpecialistServiceImpl(SpecialistRepository specialistRepository) {
        this.specialistRepository = specialistRepository;
    }

    @Override
    public void add(Specialist specialist) {
        try {
            specialistRepository.add(specialist);
        } catch (Exception e) {
            System.out.println("An error occured while adding a specialist" + e.getMessage());
        }
    }

    @Override
    public void update(Specialist specialist) {
        try {
            specialistRepository.update(specialist);
        } catch (Exception e) {
            System.out.println("An error occured while updating a specialist" + e.getMessage());
        }
    }

    @Override
    public void delete(Specialist specialist) {
        try {
            specialistRepository.delete(specialist);
        } catch (Exception e) {
            System.out.println("An error occured while deleting a specialist" + e.getMessage());
        }
    }

    @Override
    public List<Specialist> findAll() {
        try {
            return specialistRepository.findAll();
        } catch (Exception e) {
            System.out.println("An error occured while finding all specialists");
        }
        return null;
    }

    @Override
    public Specialist findById(int id) {
        try {
            return specialistRepository.findById(id);
        } catch (Exception e) {
            System.out.println("An error occured while finding a specialist");
        }
        return null;
    }

    @Override
    public Specialist findByFirstNameAndLastName(String firstName, String lastName) {
        try {
            return specialistRepository.findByFirstNameAndLastName(firstName, lastName);
        } catch (Exception e) {
            System.out.println("An error occured while finding a specialist");
        }
        return null;
    }

    @Override
    public boolean checkSpecialistImage(Specialist specialist) {
        try {
            return specialist.getPersonalImage() != null;
        } catch (Exception e) {
            System.out.println("An error occured while checking special ist image " + e.getMessage());
        }
        return false;
    }
}
