package service;

import entities.Comment;
import entities.Task;

import java.util.List;

public interface TaskService {
    void add(Task task);

    void update(Task task);

    void delete(Task task);

    List<Task> findAll();

    Task findById(int id);

    Task findByName(String name);

}
