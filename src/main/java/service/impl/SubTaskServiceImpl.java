package service.impl;

import entities.SubTask;
import repository.SubTaskRepository;
import service.SubTaskService;
import util.ValidationUtil;

import java.util.List;

public class SubTaskServiceImpl implements SubTaskService {
    private final SubTaskRepository subTaskRepository;

    public SubTaskServiceImpl(SubTaskRepository subTaskRepository) {
        this.subTaskRepository = subTaskRepository;
    }

    @Override
    public void add(SubTask subTask) {
        try {
            subTaskRepository.add(subTask);
        } catch (Exception e) {
            System.out.println("An error occured while adding a SubTask" + e.getMessage());
        }
    }

    @Override
    public void update(SubTask subTask) {
        try {
            subTaskRepository.update(subTask);
        } catch (Exception e) {
            System.out.println("An error occured while updating a SubTask" + e.getMessage());
        }
    }

    @Override
    public void delete(SubTask subTask) {
        try {
            subTaskRepository.delete(subTask);
        } catch (Exception e) {
            System.out.println("An error occured while deleting a SubTask" + e.getMessage());
        }
    }

    @Override
    public List<SubTask> findAll() {
        try {
            return subTaskRepository.findAll();
        } catch (Exception e) {
            System.out.println("An error occured while finding all SubTasks");
        }
        return null;
    }

    @Override
    public SubTask findById(int id) {
        try {
            return subTaskRepository.findById(id);
        } catch (Exception e) {
            System.out.println("An error occured while finding a SubTask" + e.getMessage());
        }
        return null;
    }

    @Override
    public SubTask findByName(String subTaskName) {
        try {
            return subTaskRepository.findByName(subTaskName);
        } catch (Exception e) {
            System.out.println("An error occured while finding a SubTask" + e.getMessage());
        }
        return null;
    }
}
