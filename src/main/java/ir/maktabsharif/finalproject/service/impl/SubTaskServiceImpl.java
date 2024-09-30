package ir.maktabsharif.finalproject.service.impl;

import ir.maktabsharif.finalproject.entities.SubTask;
import ir.maktabsharif.finalproject.exception.SubTaskOperationException;
import ir.maktabsharif.finalproject.repository.SubTaskRepository;
import ir.maktabsharif.finalproject.service.SubTaskService;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubTaskServiceImpl implements SubTaskService {
    private final ValidationUtil validationUtil;
    private final SubTaskRepository subTaskRepository;

    @Autowired
    public SubTaskServiceImpl(ValidationUtil validationUtil, SubTaskRepository subTaskRepository) {
        this.validationUtil = validationUtil;
        this.subTaskRepository = subTaskRepository;
    }

    @Override
    public void add(SubTask subTask) throws SubTaskOperationException {
        try {
            if (validationUtil.isValid(subTask)) {
                subTaskRepository.save(subTask);
            } else {
                validationUtil.displayViolations(subTask);
            }
        } catch (Exception e) {
            throw new SubTaskOperationException("An error occurred while adding a SubTask", e);
        }

    }

    @Override
    public void update(SubTask subTask) throws SubTaskOperationException {
        try {
            subTaskRepository.save(subTask);
        } catch (Exception e) {
            throw new SubTaskOperationException("An error occurred while updating a SubTask", e);
        }

    }

    @Override
    public void delete(SubTask subTask) throws SubTaskOperationException {
        try {
            subTaskRepository.delete(subTask);
        } catch (Exception e) {
            throw new SubTaskOperationException("An error occurred while deleting a SubTask", e);
        }
    }

    @Override
    public List<SubTask> findAll() throws SubTaskOperationException {
        try {
            return subTaskRepository.findAll();
        } catch (Exception e) {
            throw new SubTaskOperationException("An error occurred while finding all SubTasks", e);
        }
    }

    @Override
    public SubTask findById(int id) throws SubTaskOperationException {
        try {
            return subTaskRepository.findById(id);
        } catch (Exception e) {
            throw new SubTaskOperationException("An error occurred while finding a SubTask", e);
        }
    }

    @Override
    public SubTask findByName(String subTaskName) throws SubTaskOperationException {
        try {
            return subTaskRepository.findByName(subTaskName);
        } catch (Exception e) {
            throw new SubTaskOperationException("An error occurred while finding a SubTask", e);
        }
    }
}
