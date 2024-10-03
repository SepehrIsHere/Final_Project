package ir.maktabsharif.finalproject.repository;

import ir.maktabsharif.finalproject.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {

    @Query("SELECT t FROM Task t WHERE t.taskName = :name")
    Task findByTaskName(@Param("name") String name);

    @Query("SELECT t FROM Task t WHERE t.id = :id")
    Task findByTaskId(@Param("id") int id);
}
