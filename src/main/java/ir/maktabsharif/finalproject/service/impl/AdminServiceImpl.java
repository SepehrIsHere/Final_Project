package ir.maktabsharif.finalproject.service.impl;


import ir.maktabsharif.finalproject.entities.*;
import ir.maktabsharif.finalproject.enumerations.Role;
import ir.maktabsharif.finalproject.enumerations.SpecialistStatus;
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
    private final SubTaskService subTaskService;
    private final ValidationUtil validationUtil;

    @Override
    public void addTask(Task task) {
        try {
            if (validationUtil.isValid(task)) {
                taskService.add(task);
            } else {
                validationUtil.displayViolations(task);
            }
        } catch (Exception e) {
            System.out.println("An error occured while adding a task " + e.getMessage());
        }

    }

    @Override
    public List<Task> displayTasks() {
        try {
            return taskService.findAll();
        } catch (Exception e) {
            System.out.println("An error occured while displaying tasks " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<SubTask> displaySubTasks() {
        try {
            subTaskService.findAll();
        } catch (Exception e) {
            System.out.println("An error occured while displaying subtasks " + e.getMessage());
        }
        return null;
    }

    @Override
    public void addSubTask(SubTask subTask) {
        try {
            if (validationUtil.isValid(subTask)) {
                subTaskService.add(subTask);
            } else {
                validationUtil.displayViolations(subTask);
            }
        } catch (Exception e) {
            System.out.println("An error occured while adding a subtask " + e.getMessage());
        }
    }

    @Override
    public void addSpecialistToSubTask(Specialist specialist, SubTask subTask) {
        try {
            if (validationUtil.isValid(specialist) && validationUtil.isValid(subTask)) {
                List<Specialist> specialists = new ArrayList<>();
                specialists.add(specialist);
                subTask.setSpecialists(specialists);
                subTaskService.update(subTask);
                specialistService.update(specialist);
            } else {
                System.out.println("Violations of Specialist : ");
                validationUtil.displayViolations(specialist);

                System.out.println("Violations of subtask : ");
                validationUtil.displayViolations(subTask);
            }
        } catch (Exception e) {
            System.out.println("An error occured while adding a specialist to a subtask " + e.getMessage());
        }
    }

    @Override
    public void deleteSubTask(SubTask subTask) {
        try {
            subTaskService.delete(subTask);
        } catch (Exception e) {
            System.out.println("An error occured while deleting a subtask " + e.getMessage());
        }
    }

    @Override
    public void deleteTask(Task task) {
        try {
            taskService.delete(task);
        } catch (Exception e) {
            System.out.println("An error occured while deleting a task " + e.getMessage());
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
    public void changeSpecialistStatus(Specialist specialist, SpecialistStatus specialistStatus) {
        try {
            specialist.setSpecialistStatus(specialistStatus);
            specialistService.update(specialist);
        } catch (Exception e) {
            System.out.println("An error occured while updating specialist status " + e.getMessage());
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

    private boolean isUserAdmin(Users users) {
        return users.getRole().equals(Role.ADMINISTRATOR) && !(users instanceof Customer) && !(users instanceof Specialist);
    }
}


