package ir.maktabsharif.finalproject.service;



import ir.maktabsharif.finalproject.dto.SubTaskDto;
import ir.maktabsharif.finalproject.dto.TaskDto;
import ir.maktabsharif.finalproject.entities.SubTask;
import ir.maktabsharif.finalproject.exception.SubTaskOperationException;

import java.util.List;

public interface SubTaskService {
    void add(SubTask subTask) throws SubTaskOperationException;

    void update(SubTask subTask) throws SubTaskOperationException;

    void delete(SubTask subTask) throws SubTaskOperationException;

    List<SubTask> findAll() throws SubTaskOperationException;

    SubTask findById(int id) throws SubTaskOperationException;

    SubTask findByName(String subTaskName) throws SubTaskOperationException;

    List<SubTask> findSubTaskByTask(String taskName) throws SubTaskOperationException;

    void changeBasePrice(String subTaskName,Double newBasePrice) throws SubTaskOperationException;

    void changeDescription(String subTaskName,String description) throws SubTaskOperationException;

    boolean doesSubTaskExist(SubTaskDto subTaskDto);
}
