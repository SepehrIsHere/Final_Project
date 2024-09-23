package service;

import entities.Specialist;
import entities.SubTask;
import entities.Task;
import entities.Users;
import enumerations.SpecialistStatus;

import java.math.BigDecimal;
import java.util.List;

public interface AdminService {
    void addTask(Task task);

    List<Task> displayTasks();

    List<SubTask> displaySubTasks();

    void addSubTask(SubTask subTask);

    void addSpecialistToSubTask(Specialist specialist, SubTask subTask);

    void deleteSubTask(SubTask subTask);

    void deleteTask(Task task);

    void changeBasePriceOfSubTask(SubTask subTask, BigDecimal basePrice);

    void changeDescriptionOfSubTask(SubTask subTask, String description);


    void changeSpecialistStatus(Specialist specialist, SpecialistStatus specialistStatus);


}
