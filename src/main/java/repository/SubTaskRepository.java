package repository;

import entities.SubTask;

import java.util.List;

public interface SubTaskRepository extends BaseEntityRepository<SubTask> {
    SubTask findById(int id);

    List<SubTask> findAll();

    SubTask findByName(String subTaskName);
}
