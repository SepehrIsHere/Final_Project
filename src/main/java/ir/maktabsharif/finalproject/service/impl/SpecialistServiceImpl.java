package ir.maktabsharif.finalproject.service.impl;


import ir.maktabsharif.finalproject.dto.SpecialistDto;
import ir.maktabsharif.finalproject.dto.SubTaskDto;
import ir.maktabsharif.finalproject.entities.*;
import ir.maktabsharif.finalproject.enumerations.Role;
import ir.maktabsharif.finalproject.enumerations.SpecialistStatus;
import ir.maktabsharif.finalproject.exception.SpecialistNotFoundException;
import ir.maktabsharif.finalproject.exception.SpecialistOperationException;
import ir.maktabsharif.finalproject.repository.SpecialistRepository;
import ir.maktabsharif.finalproject.service.*;
import ir.maktabsharif.finalproject.util.MapperUtil;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialistServiceImpl implements SpecialistService {
    private final ValidationUtil validationUtil;
    private final SpecialistRepository specialistRepository;
    private final MapperUtil mapperUtil;

    @Override
    public SpecialistDto signUp(SpecialistDto specialistDto) throws SpecialistOperationException {
        Specialist specialist = Specialist.builder()
                .firstName(specialistDto.getFirstname())
                .lastName(specialistDto.getLastname())
                .email(specialistDto.getEmail())
                .username(specialistDto.getUsername())
                .password(specialistDto.getPassword())
                .role(Role.SPECIALIST)
                .specialistStatus(SpecialistStatus.PENDING_APPROVAL)
                .score(0.0)
                .build();
        add(specialist);
        return mapperUtil.convertToDto(specialist);
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
    public boolean checkSpecialistImage(SpecialistDto specialistDto) throws SpecialistOperationException {
        try {
            Specialist specialist =
                    specialistRepository.findByFirstNameAndLastName(specialistDto.getFirstname(), specialistDto.getLastname());
            if (findSpecialist(specialistDto) != null) {
                return specialist.getPersonalImage() != null;
            } else {
                throw new SpecialistNotFoundException("Specialist not found");
            }
        } catch (Exception e) {
            throw new SpecialistOperationException("An error occured while checking specialist personal image", e);
        }
    }

    @Override
    public List<SubTaskDto> specialistSubTasks(SpecialistDto specialistDto) {
        Specialist specialist = findSpecialist(specialistDto);
        if (specialist != null) {
            List<SubTask> specialistSubTasks = specialist.getSubTasks();
            if (specialistSubTasks != null) {
                List<SubTaskDto> subTaskDtos = new ArrayList<>();
                for (SubTask subTask : specialistSubTasks) {
                    subTaskDtos.add(mapperUtil.convertToDto(subTask));
                }
                return subTaskDtos;
            } else {
                throw new SpecialistNotFoundException("Specialist list is null or empty");
            }
        } else {
            throw new SpecialistNotFoundException("Specialist Not Found ! ");
        }
    }

    private Specialist findSpecialist(SpecialistDto specialistDto) {
        return specialistRepository.findByFirstNameAndLastName(specialistDto.getFirstname(), specialistDto.getLastname());
    }

}
