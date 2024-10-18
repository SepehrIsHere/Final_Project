package ir.maktabsharif.finalproject.service.impl;

import ir.maktabsharif.finalproject.entities.Task;
import ir.maktabsharif.finalproject.exception.InvalidFieldValueException;
import ir.maktabsharif.finalproject.exception.TaskOperationException;
import ir.maktabsharif.finalproject.repository.TaskRepository;
import ir.maktabsharif.finalproject.service.TaskService;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ValidationUtil validationUtil;

    @Override
    public void add(Task task) throws TaskOperationException {
        try {
            if (validationUtil.isValid(task)) {
                taskRepository.save(task);
            } else {
                throw new InvalidFieldValueException("Some fields of task are not valid");
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
            return taskRepository.findByTaskId(id);
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

    @Override
    public void changeTaskName(String taskName, String newName) throws TaskOperationException {
        try{
            Task task = findByName(taskName);
            task.setTaskName(newName);
            update(task);
        }catch (Exception e){
            throw new TaskOperationException("An error occurred while changing a task name", e);
        }
    }

    @Override
    public void changeTaskDescription(String taskName, String newDescription) throws TaskOperationException {
        try{
            Task task = findByName(taskName);
            task.setTaskDescription(newDescription);
            update(task);
        }catch (Exception e){
            throw new TaskOperationException("An error occurred while changing a task description", e);
        }
    }

    @Override
    public void deleteAll() throws TaskOperationException {
        taskRepository.deleteAll();
    }
}
