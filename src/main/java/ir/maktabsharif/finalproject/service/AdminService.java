package ir.maktabsharif.finalproject.service;


import ir.maktabsharif.finalproject.dto.SpecialistDto;
import ir.maktabsharif.finalproject.entities.Specialist;
import ir.maktabsharif.finalproject.entities.SubTask;
import ir.maktabsharif.finalproject.entities.Task;
import ir.maktabsharif.finalproject.entities.Users;
import ir.maktabsharif.finalproject.enumerations.Role;
import ir.maktabsharif.finalproject.exception.SpecialistOperationException;

import java.util.List;

public interface AdminService {

    void changeBasePriceOfSubTask(SubTask subTask, Double basePrice);

    void changeDescriptionOfSubTask(SubTask subTask, String description);

    void removeSpecialistFromSubTask(Specialist specialist, SubTask subTask);

    void approveSpecialist(String specialistFirstName,String specialistLastName) throws SpecialistOperationException;

    void addSubTaskToSpecialist(String specialistFirstName, String specialistLastName, String subTaskName) throws SpecialistOperationException;

    void removeSubTaskFromSpecialist(String specialistFirstName, String specialistLastName, String subTaskName) throws SpecialistOperationException;
}
