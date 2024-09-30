package ir.maktabsharif.finalproject.repository;

import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.entities.Suggestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestionsRepository extends JpaRepository<Suggestions, Integer> {
    @Query("SELECT s FROM Suggestions s WHERE s.order = :order")
    List<Suggestions> findByOrder(@Param("order") Order order);

    @Query("SELECT s FROM Suggestions s WHERE s.id = :id")
    Suggestions findById(@Param("id") int id);
}
