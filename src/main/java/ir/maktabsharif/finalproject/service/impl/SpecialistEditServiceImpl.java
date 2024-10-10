package ir.maktabsharif.finalproject.service.impl;

import ir.maktabsharif.finalproject.dto.SpecialistDto;
import ir.maktabsharif.finalproject.entities.Specialist;
import ir.maktabsharif.finalproject.enumerations.SpecialistStatus;
import ir.maktabsharif.finalproject.exception.IllegalImageException;
import ir.maktabsharif.finalproject.exception.SpecialistNotFoundException;
import ir.maktabsharif.finalproject.repository.SpecialistRepository;
import ir.maktabsharif.finalproject.service.SpecialistEditService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import ir.maktabsharif.finalproject.util.PersonalImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class SpecialistEditServiceImpl implements SpecialistEditService {
    private final SpecialistRepository specialistRepository;
    private PersonalImageUtil personalImageUtil;
    private MapperUtil mapperUtil;

    @Override
    public void updateFirstName(SpecialistDto specialistDto, String newFirstName) {
        Specialist specialist = specialistRepository.findByFirstNameAndLastName(specialistDto.firstname(), specialistDto.lastname());
        if (specialist != null) {
            specialist.setFirstName(newFirstName);
            specialistRepository.save(specialist);
        } else {
            throw new SpecialistNotFoundException("Specialist Not Found !");
        }
    }

    @Override
    public void updateLastName(SpecialistDto specialistDto, String newLastName) {
        Specialist specialist = specialistRepository.findByFirstNameAndLastName(specialistDto.firstname(), specialistDto.lastname());
        if (specialist != null) {
            specialist.setLastName(newLastName);
            specialistRepository.save(specialist);
        } else {
            throw new SpecialistNotFoundException("Specialist Not Found !");
        }
    }

    @Override
    public void updateUsername(SpecialistDto specialistDto, String newUsername) {
        Specialist specialist = specialistRepository.findByFirstNameAndLastName(specialistDto.firstname(), specialistDto.lastname());
        if (specialist != null) {
            specialist.setUsername(newUsername);
            specialistRepository.save(specialist);
        } else {
            throw new SpecialistNotFoundException("Specialist Not Found !");
        }
    }

    @Override
    public void updatePassword(SpecialistDto specialistDto, String newPassword) {
        Specialist specialist = specialistRepository.findByFirstNameAndLastName(specialistDto.firstname(), specialistDto.lastname());
        if (specialist != null) {
            specialist.setPassword(newPassword);
            specialistRepository.save(specialist);
        } else {
            throw new SpecialistNotFoundException("Specialist Not Found !");
        }
    }

    @Override
    public void changeSpecialistStatus(SpecialistDto specialistDto, SpecialistStatus newStatus) {
        Specialist specialist = specialistRepository.findByFirstNameAndLastName(specialistDto.firstname(), specialistDto.lastname());
        if (specialist != null) {
            specialist.setSpecialistStatus(newStatus);
            specialistRepository.save(specialist);
        } else {
            throw new SpecialistNotFoundException("Specialist Not Found !");
        }
    }

    @Override
    public void addPersonalImage(SpecialistDto specialistDto, File filepath) {
        Specialist specialist = specialistRepository.findByFirstNameAndLastName(specialistDto.firstname(), specialistDto.lastname());
        if (specialist != null) {
            if (personalImageUtil.isImageValid(filepath)) {
                specialist.setPersonalImage(personalImageUtil.writeImage(filepath));
                specialistRepository.save(specialist);
            } else {
                throw new IllegalImageException("Image is not valid ! ");
            }
        } else {
            throw new SpecialistNotFoundException("Specialist Not Found !");
        }
    }

    @Override
    public void removePersonalImage(SpecialistDto specialistDto) {
        Specialist specialist = specialistRepository.findByFirstNameAndLastName(specialistDto.firstname(), specialistDto.lastname());
        if (specialist != null) {
            specialist.setPersonalImage(null);
            specialistRepository.save(specialist);
        } else {
            throw new SpecialistNotFoundException("Specialist Not Found !");
        }
    }
}
