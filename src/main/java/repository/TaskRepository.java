package repository;

import entities.Task;

import java.util.List;

public interface TaskRepository extends BaseEntityRepository<Task> {
    Task findById(int id);

    List<Task> findAll();

    Task findByName(String name);

}
