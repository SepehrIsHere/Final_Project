package service;

import entities.Comment;
import entities.SubTask;

import java.util.List;

public interface SubTaskService {
    void add(SubTask subTask);

    void update(SubTask subTask);

    void delete(SubTask subTask);

    List<SubTask> findAll();

    SubTask findById(int id);

    SubTask findByName(String subTaskName);
}
