package ir.maktabsharif.finalproject.service;

import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.dto.SpecialistDto;
import ir.maktabsharif.finalproject.dto.SuggestionDto;
import ir.maktabsharif.finalproject.exception.OrderOperationException;
import ir.maktabsharif.finalproject.exception.SuggestionOperationException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CustomerSuggestionService {
    List<SuggestionDto> displaySuggestionsForOrder(OrderDto orderDto);

    List<SuggestionDto> displaySuggestionBaseOnSpecialistRating(OrderDto orderDto);

    List<SuggestionDto> displaySuggestionBasedOnSuggestedPrice(OrderDto orderDto);
}
