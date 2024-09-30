package ir.maktabsharif.finalproject.service.impl;



import ir.maktabsharif.finalproject.entities.Specialist;
import ir.maktabsharif.finalproject.exception.SpecialistOperationException;
import ir.maktabsharif.finalproject.repository.SpecialistRepository;
import ir.maktabsharif.finalproject.service.SpecialistService;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialistServiceImpl implements SpecialistService {
    private final ValidationUtil validationUtil;
    private final SpecialistRepository specialistRepository;

    @Autowired
    public SpecialistServiceImpl(ValidationUtil validationUtil, SpecialistRepository specialistRepository) {
        this.validationUtil = validationUtil;
        this.specialistRepository = specialistRepository;
    }

    @Override
    public void add(Specialist specialist) throws SpecialistOperationException {
        try {
            if (validationUtil.isValid(specialist)) {
                specialistRepository.save(specialist);
            } else {
                validationUtil.displayViolations(specialist);
            }
        } catch (Exception e) {
            throw new SpecialistOperationException("An error occured while adding specialist", e);
        }

    }

    @Override
    public void update(Specialist specialist) throws SpecialistOperationException {
        try {
            specialistRepository.save(specialist);
        } catch (Exception e) {
            throw new SpecialistOperationException("An error occured while updating specialist", e);
        }

    }

    @Override
    public void delete(Specialist specialist) throws SpecialistOperationException {
        try {
            specialistRepository.delete(specialist);
        } catch (Exception e) {
            throw new SpecialistOperationException("An error occured while deleting specialist", e);
        }
    }

    @Override
    public List<Specialist> findAll() throws SpecialistOperationException {
        try {
            return specialistRepository.findAll();
        } catch (Exception e) {
            throw new SpecialistOperationException("An error occured while finding all specialists", e);
        }
    }

    @Override
    public Specialist findById(int id) throws SpecialistOperationException {
        try {
            return specialistRepository.findSpecialistById(id);
        } catch (Exception e) {
            throw new SpecialistOperationException("An error occured while finding specialist", e);
        }
    }

    @Override
    public Specialist findByFirstNameAndLastName(String firstName, String lastName) throws SpecialistOperationException {
        try {
            return specialistRepository.findByFirstNameAndLastName(firstName, lastName);
        } catch (Exception e) {
            throw new SpecialistOperationException("An error occured while finding specialist", e);
        }
    }

    @Override
    public boolean checkSpecialistImage(Specialist specialist) throws SpecialistOperationException {
        try {
            return specialist.getPersonalImage() != null;
        } catch (Exception e) {
            throw new SpecialistOperationException("An error occured while checking specialist personal image", e);
        }

    }
}
