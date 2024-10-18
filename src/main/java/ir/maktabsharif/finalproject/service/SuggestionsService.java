package ir.maktabsharif.finalproject.service;


import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.dto.SpecialistDto;
import ir.maktabsharif.finalproject.dto.SuggestionDto;
import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.entities.Specialist;
import ir.maktabsharif.finalproject.entities.Suggestions;
import ir.maktabsharif.finalproject.exception.SuggestionOperationException;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SuggestionsService {
    void add(Suggestions suggestion) throws SuggestionOperationException;

    void update(Suggestions suggestion) throws SuggestionOperationException;

    void delete(Suggestions suggestion) throws SuggestionOperationException;

    Suggestions findById(int id) throws SuggestionOperationException;

    List<Suggestions> findAll() throws SuggestionOperationException;

    List<Suggestions> findByOrder(Order order) throws SuggestionOperationException;

    List<Suggestions> findByOrderOrderBySpecialistScoreDesc(Order order) throws SuggestionOperationException;

    List<Suggestions> findByOrderOrderBySuggestedPriceDesc(Order order) throws SuggestionOperationException;

    Suggestions findSuggestionsByNameOfOrderAndSpecialist(String nameOfOrder, String specialistFirstName, String specialistLastName) throws SuggestionOperationException;

    Suggestions findSuggestionByCustomerAndNameOfOrder( Customer customer, String nameOfOrder) throws SuggestionOperationException;

}
