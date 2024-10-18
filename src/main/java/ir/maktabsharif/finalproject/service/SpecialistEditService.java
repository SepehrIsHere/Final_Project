package ir.maktabsharif.finalproject.service;

import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.dto.SpecialistDto;
import ir.maktabsharif.finalproject.enumerations.SpecialistStatus;
import ir.maktabsharif.finalproject.exception.SpecialistOperationException;

import java.io.File;
import java.util.List;

public interface SpecialistEditService {
    void updateFirstName(SpecialistDto specialistDto, String newFirstName);

    void updateLastName(SpecialistDto specialistDto, String newLastName);

    void updateUsername(SpecialistDto specialistDto, String newUsername);

    void updatePassword(SpecialistDto specialistDto, String newPassword);

    void addPersonalImage(SpecialistDto specialistDto, File filepath);

    void removePersonalImage(SpecialistDto specialistDto);

    void changeSpecialistStatus(SpecialistDto specialistDto, SpecialistStatus newStatus);


}
