package ir.maktabsharif.finalproject.service;


import ir.maktabsharif.finalproject.entities.Task;
import ir.maktabsharif.finalproject.exception.TaskOperationException;

import java.util.List;

public interface TaskService {
    void add(Task task) throws TaskOperationException;

    void update(Task task) throws TaskOperationException;

    void delete(Task task) throws TaskOperationException;

    void deleteAll() throws TaskOperationException;

    List<Task> findAll() throws TaskOperationException;

    Task findById(int id) throws TaskOperationException;

    Task findByName(String name) throws TaskOperationException;

}
