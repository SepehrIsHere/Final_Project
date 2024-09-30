package service;

import entities.Order;
import entities.Suggestions;

import java.util.List;

public interface SuggestionsService {
    void add(Suggestions suggestion);

    void update(Suggestions suggestion);

    void delete(Suggestions suggestion);

    Suggestions findById(int id);

    List<Suggestions> findAll();

    List<Suggestions> findByOrder(Order order);
}
