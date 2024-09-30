package ir.maktabsharif.finalproject.service.impl;

import ir.maktabsharif.finalproject.entities.Task;
import ir.maktabsharif.finalproject.exception.TaskOperationException;
import ir.maktabsharif.finalproject.repository.TaskRepository;
import ir.maktabsharif.finalproject.service.TaskService;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ValidationUtil validationUtil;

   @Autowired
    public TaskServiceImpl(ValidationUtil validationUtil, TaskRepository taskRepository) {
        this.validationUtil = validationUtil;
        this.taskRepository = taskRepository;
    }

    @Override
    public void add(Task task) throws TaskOperationException {
        try {
            if (validationUtil.isValid(task)) {
                taskRepository.save(task);
            } else {
                validationUtil.displayViolations(task);
            }
        } catch (Exception e) {
            throw new TaskOperationException("An error occurred while adding a task", e);
        }
    }

    @Override
    public void update(Task task) throws TaskOperationException {
        try {
            taskRepository.save(task);
        } catch (Exception e) {
            throw new TaskOperationException("An error occurred while updating a task", e);
        }
    }

    @Override
    public void delete(Task task) throws TaskOperationException {
        try {
            taskRepository.delete(task);
        } catch (ConstraintViolationException e) {
            throw new TaskOperationException("Error while deleting task", e);
        }
    }

    @Override
    public List<Task> findAll() throws TaskOperationException {
        try {
            return taskRepository.findAll();
        } catch (Exception e) {
            throw new TaskOperationException("An error occurred while finding all tasks", e);
        }
    }

    @Override
    public Task findById(int id) throws TaskOperationException {
        try {
            return taskRepository.findById(id);
        } catch (Exception e) {
            throw new TaskOperationException("An error occurred while finding a task", e);
        }
    }

    @Override
    public Task findByName(String name) throws TaskOperationException {
        try {
            return taskRepository.findByTaskName(name);
        } catch (Exception e) {
            throw new TaskOperationException("An error occurred while finding a task", e);
        }

    }
}
