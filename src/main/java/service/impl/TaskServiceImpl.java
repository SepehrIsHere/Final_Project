package service.impl;

import entities.Task;
import repository.TaskRepository;
import service.TaskService;
import util.ValidationUtil;

import java.util.List;

public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ValidationUtil validationUtil;

    public TaskServiceImpl(TaskRepository taskRepository, ValidationUtil validationUtil) {
        this.taskRepository = taskRepository;
        this.validationUtil = validationUtil;
    }

    @Override
    public void add(Task task) {
        try {
            validationUtil.validation(task);
            taskRepository.add(task);
        } catch (Exception e) {
            System.out.println("An error occured while adding a task" + e.getMessage());
        }
    }

    @Override
    public void update(Task task) {
        try {
            validationUtil.validation(task);
            taskRepository.update(task);
        } catch (Exception e) {
            System.out.println("An error occured while updating a task" + e.getMessage());
        }
    }

    @Override
    public void delete(Task task) {
        try {
            taskRepository.delete(task);
        } catch (Exception e) {
            System.out.println("An error occured while deleting a task" + e.getMessage());
        }
    }

    @Override
    public List<Task> findAll() {
        try {
            return taskRepository.findAll();
        } catch (Exception e) {
            System.out.println("An error occured while finding all tasks");
        }
        return null;
    }

    @Override
    public Task findById(int id) {
        try {
            return taskRepository.findById(id);
        } catch (Exception e) {
            System.out.println("An error occured while finding a task");
        }
        return null;
    }

    @Override
    public Task findByName(String name) {
        try {
            return taskRepository.findByName(name);
        } catch (Exception e) {
            System.out.println("An error occured while finding a task");
        }
        return null;
    }
}
