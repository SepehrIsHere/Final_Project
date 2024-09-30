package ir.maktabsharif.finalproject.service;


import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.entities.Suggestions;
import ir.maktabsharif.finalproject.exception.SuggestionOperationException;

import java.util.List;

public interface SuggestionsService {
    void add(Suggestions suggestion) throws SuggestionOperationException;

    void update(Suggestions suggestion) throws SuggestionOperationException;

    void delete(Suggestions suggestion) throws SuggestionOperationException;

    Suggestions findById(int id) throws SuggestionOperationException;

    List<Suggestions> findAll() throws SuggestionOperationException;

    List<Suggestions> findByOrder(Order order) throws SuggestionOperationException;
}
