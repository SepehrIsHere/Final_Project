package ir.maktabsharif.finalproject.service;


import ir.maktabsharif.finalproject.entities.Specialist;
import ir.maktabsharif.finalproject.entities.SubTask;
import ir.maktabsharif.finalproject.entities.Task;
import ir.maktabsharif.finalproject.enumerations.SpecialistStatus;

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

    void changeBasePriceOfSubTask(SubTask subTask, Double basePrice);

    void changeDescriptionOfSubTask(SubTask subTask, String description);

    void removeSpecialistFromSubTask(Specialist specialist, SubTask subTask);

    void changeSpecialistStatus(Specialist specialist, SpecialistStatus specialistStatus);


}
