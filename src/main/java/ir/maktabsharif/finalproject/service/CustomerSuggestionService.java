package ir.maktabsharif.finalproject.service;

import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.dto.SuggestionDto;
import ir.maktabsharif.finalproject.exception.InvalidFieldValueException;
import ir.maktabsharif.finalproject.exception.OrderOperationException;
import ir.maktabsharif.finalproject.exception.SuggestionOperationException;

import java.util.List;

public interface CustomerSuggestionService {
    List<SuggestionDto> displaySuggestionsForOrder(OrderDto orderDto);

    List<SuggestionDto> displaySuggestionBaseOnSpecialistRating(OrderDto orderDto);

    List<SuggestionDto> displaySuggestionBasedOnSuggestedPrice(OrderDto orderDto);

//    void changeStatusToStarted(SuggestionDto suggestionDto) throws InvalidFieldValueException, OrderOperationException;

    void changeStatusToFinished(SuggestionDto suggestionDto) throws InvalidFieldValueException, OrderOperationException, SuggestionOperationException;

    void changeStatusToPaid(SuggestionDto suggestionDto) throws SuggestionOperationException, OrderOperationException;

}
