package ir.maktabsharif.finalproject.service.impl;

import ir.maktabsharif.finalproject.dto.SubTaskDto;
import ir.maktabsharif.finalproject.dto.TaskDto;
import ir.maktabsharif.finalproject.entities.SubTask;
import ir.maktabsharif.finalproject.exception.SubTaskNotFoundException;
import ir.maktabsharif.finalproject.exception.SubTaskOperationException;
import ir.maktabsharif.finalproject.repository.SubTaskRepository;
import ir.maktabsharif.finalproject.service.SubTaskService;
import ir.maktabsharif.finalproject.service.TaskService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubTaskServiceImpl implements SubTaskService {
    private final ValidationUtil validationUtil;
    private final SubTaskRepository subTaskRepository;
    private final TaskService taskService;
    private final MapperUtil mapperUtil;

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
            return subTaskRepository.findSubTaskById(id);
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

    @Override
    public List<SubTask> findSubTaskByTask(String taskName) throws SubTaskOperationException {
        try {
            return subTaskRepository.findSubTaskByTask(taskService.findByName(taskName));
        } catch (Exception e) {
            throw new SubTaskOperationException("An error occurred while finding a SubTask", e);
        }
    }

    @Override
    public void changeBasePrice(String subTaskName, Double newBasePrice) throws SubTaskOperationException {
        try {
            SubTask subTask = subTaskRepository.findByName(subTaskName);
            if (subTask != null) {
                subTask.setBasePrice(newBasePrice);
                subTaskRepository.save(subTask);
            } else {
                throw new SubTaskNotFoundException("Subtask not found ! ");
            }
        } catch (Exception e) {
            throw new SubTaskOperationException("An error occured while updating base price", e);
        }
    }

    @Override
    public void changeDescription(String subTaskName, String description) throws SubTaskOperationException {
        try {
            SubTask subTask = subTaskRepository.findByName(subTaskName);
            if (subTask != null) {
                subTask.setDescription(description);
                subTaskRepository.save(subTask);
            } else {
                throw new SubTaskNotFoundException("Subtask not found ! ");
            }
        } catch (Exception e) {
            throw new SubTaskOperationException("An error occured while updating description ", e);
        }
    }

    @Override
    public boolean doesSubTaskExist(SubTaskDto subTaskDto) {
        return subTaskRepository.findByName(subTaskDto.getSubTaskName()) != null;
    }
}
