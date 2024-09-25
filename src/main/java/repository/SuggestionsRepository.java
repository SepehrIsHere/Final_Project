package repository;

import entities.Order;
import entities.Suggestions;

import java.util.List;

public interface SuggestionsRepository extends BaseEntityRepository<Suggestions> {
    List<Suggestions> findAll();

    Suggestions findById(int id);

    List<Suggestions> findByOrder(Order order);
}
