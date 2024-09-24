package service.impl;

import entities.*;
import enumerations.Role;
import enumerations.SpecialistStatus;
import service.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AdminServiceImpl implements AdminService {
    private final TaskService taskService;
    private final SpecialistService specialistService;
    private final SubTaskService subTaskService;

    public AdminServiceImpl(TaskService taskService, SpecialistService specialistService, SubTaskService subTaskService) {
        this.taskService = taskService;
        this.specialistService = specialistService;
        this.subTaskService = subTaskService;
    }

    @Override
    public void addTask(Task task) {
        try {
            taskService.add(task);
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
            subTaskService.add(subTask);
        } catch (Exception e) {
            System.out.println("An error occured while adding a subtask " + e.getMessage());
        }
    }

    @Override
    public void addSpecialistToSubTask(Specialist specialist, SubTask subTask) {
        try {
            List<Specialist> specialists = new ArrayList<>();
            specialists.add(specialist);
            subTask.setSpecialists(specialists);
            subTaskService.update(subTask);
            specialistService.update(specialist);
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
    public void changeBasePriceOfSubTask(SubTask subTask, BigDecimal basePrice) {
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


