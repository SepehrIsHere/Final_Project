package ir.maktabsharif.finalproject.service.impl;


import ir.maktabsharif.finalproject.dto.SpecialistDto;
import ir.maktabsharif.finalproject.entities.*;
import ir.maktabsharif.finalproject.enumerations.Role;
import ir.maktabsharif.finalproject.enumerations.SpecialistStatus;
import ir.maktabsharif.finalproject.exception.SpecialistNotFoundException;
import ir.maktabsharif.finalproject.exception.SpecialistOperationException;
import ir.maktabsharif.finalproject.exception.SubTaskNotFoundException;
import ir.maktabsharif.finalproject.repository.SpecialistRepository;
import ir.maktabsharif.finalproject.repository.SubTaskRepository;
import ir.maktabsharif.finalproject.service.AdminService;
import ir.maktabsharif.finalproject.service.SpecialistService;
import ir.maktabsharif.finalproject.service.SubTaskService;
import ir.maktabsharif.finalproject.service.TaskService;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final TaskService taskService;
    private final SpecialistService specialistService;
    private final SpecialistRepository specialistRepository;
    private final SubTaskRepository subTaskRepository;
    private final SubTaskService subTaskService;
    private final ValidationUtil validationUtil;

    @Override
    public void addSubTaskToSpecialist(String specialistFirstName, String specialistLastName,String subTaskName) throws SpecialistOperationException {
        Specialist specialist = specialistRepository.findByFirstNameAndLastName(specialistFirstName, specialistLastName);
        SubTask subTask = subTaskRepository.findByName(subTaskName);
        if (specialist != null) {
            if (subTask != null) {
                List<SubTask> specialistSubTasks = specialist.getSubTasks();
                if (specialistSubTasks != null && !specialistSubTasks.contains(subTask)) {
                    specialistSubTasks.add(subTask);
                    specialist.setSubTasks(specialistSubTasks);
                    specialistService.update(specialist);
                }
            } else {
                throw new SubTaskNotFoundException("SubTask Not Found ! ");
            }
        } else {
            throw new SpecialistNotFoundException("Specialist Not Found ! ");
        }
    }

    @Override
    public void removeSubTaskFromSpecialist(String specialistFirstName, String specialistLastName,String subTaskName) throws SpecialistOperationException {
        Specialist specialist = specialistRepository.findByFirstNameAndLastName(specialistFirstName, specialistLastName);
        SubTask subTask = subTaskRepository.findByName(subTaskName);
        if (specialist != null) {
            if (subTask != null) {
                List<SubTask> specialistSubTasks = specialist.getSubTasks();
                if (specialistSubTasks != null) {
                    specialistSubTasks.remove(subTask);
                    specialistService.update(specialist);
                }
            } else {
                throw new SubTaskNotFoundException("SubTask Not Found ! ");
            }
        } else {
            throw new SpecialistNotFoundException("Specialist Not Found ! ");
        }
    }

    @Override
    public void changeBasePriceOfSubTask(SubTask subTask, Double basePrice) {
        try {
            subTask.setBasePrice(basePrice);
            subTaskService.update(subTask);
        } catch (Exception e) {
            System.out.println("An error occured while changing basePrice of a subtask " + e.getMessage());
        }
    }

    @Override
    public void changeDescriptionOfSubTask(SubTask subTask, String description) {
        try {
            subTask.setDescription(description);
            subTaskService.update(subTask);
        } catch (Exception e) {
            System.out.println("An error occured while changing description of a subtask " + e.getMessage());
        }
    }

    @Override
    public void approveSpecialist(String specialistFirstName,String specialistLastName) throws SpecialistOperationException {
        try{
            Specialist specialist = specialistService.findByFirstNameAndLastName(specialistFirstName,specialistLastName);
            if(specialist != null){
                specialist.setSpecialistStatus(SpecialistStatus.APPROVED);
                specialistService.update(specialist);
            }else{
                throw new SpecialistNotFoundException("Specialist Not Found" );
            }
        }catch (Exception e){
            throw new SpecialistOperationException("An error occured approving Specialist",e);
        }
    }

    @Override
    public void removeSpecialistFromSubTask(Specialist specialist, SubTask subTask) {
        try {
            List<Specialist> specialists = subTask.getSpecialists();
            for (Specialist s : specialists) {
                if (s.equals(specialist)) {
                    specialists.remove(s);
                    subTaskService.update(subTask);
                }
            }
            List<SubTask> subTasks = specialist.getSubTasks();
            for (SubTask s : subTasks) {
                if (s.equals(subTask)) {
                    subTasks.remove(subTask);
                    specialistService.update(specialist);
                }
            }
        } catch (Exception e) {
            System.out.println("An error occured while removing a subtask " + e.getMessage());
        }
    }

}


