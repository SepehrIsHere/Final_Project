package ir.maktabsharif.finalproject.repository;

import ir.maktabsharif.finalproject.dto.SuggestionDto;
import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.entities.Specialist;
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
    Suggestions findSuggestionsById(@Param("id") int id);

    @Query("SELECT s FROM Suggestions s WHERE s.order = :order ORDER BY s.specialist.score DESC")
    List<Suggestions> findByOrderOrderBySpecialistScoreDesc(Order order);

    @Query("SELECT s FROM Suggestions s WHERE s.order = :order ORDER BY s.suggestedPrice DESC")
    List<Suggestions> findByOrderOrderBySuggestedPriceDesc(Order order);

    @Query("SELECT s FROM Suggestions s WHERE s.customer = :customer AND s.order.nameOfOrder = :nameOfOrder")
    Suggestions findSuggestionByCustomerAndNameOfOrder(@Param("customer") Customer customer,@Param("nameOfOrder") String nameOfOrder);

    @Query("SELECT s FROM Suggestions s WHERE s.order.nameOfOrder = :nameOfOrder AND s.specialist = :specialist")
    Suggestions findSuggestionsByNameOfOrderAndSpecialist(@Param("nameOfOrder") String nameOfOrder, @Param("specialist") Specialist specialist);
}
