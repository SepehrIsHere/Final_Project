package ir.maktabsharif.finalproject.service;

import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.dto.SpecialistDto;
import ir.maktabsharif.finalproject.dto.SuggestionDto;
import ir.maktabsharif.finalproject.exception.SuggestionOperationException;

import java.time.LocalDate;
import java.time.LocalTime;

public interface SpecialistSuggestionService {
    SuggestionDto createSuggestionForOrder(Double suggestedPrice, LocalDate suggestedDate, LocalTime suggestedTime, OrderDto orderDto, SpecialistDto specialistDto) throws SuggestionOperationException;

    void removeSuggestionForOrder(SuggestionDto suggestionDto) throws SuggestionOperationException;
}
