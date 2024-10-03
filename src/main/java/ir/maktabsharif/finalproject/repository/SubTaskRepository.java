package ir.maktabsharif.finalproject.repository;

import ir.maktabsharif.finalproject.entities.SubTask;
import ir.maktabsharif.finalproject.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubTaskRepository extends JpaRepository<SubTask, Integer> {

    @Query("SELECT s FROM SubTask s WHERE s.name = :name")
    SubTask findByName(@Param("name") String name);

    @Query("SELECT s FROM SubTask s WHERE s.task = :task")
    List<SubTask> findTasksSubTasks(@Param("task") Task task);

    @Query("SELECT s FROM SubTask s WHERE s.id = :id")
    SubTask findSubTaskById(@Param("id") int id);

}
