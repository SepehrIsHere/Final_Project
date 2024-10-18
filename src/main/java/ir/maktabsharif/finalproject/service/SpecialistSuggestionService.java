package ir.maktabsharif.finalproject.service;

import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.dto.SpecialistDto;
import ir.maktabsharif.finalproject.dto.SuggestionDto;
import ir.maktabsharif.finalproject.exception.InvalidFieldValueException;
import ir.maktabsharif.finalproject.exception.SpecialistOperationException;
import ir.maktabsharif.finalproject.exception.SubTaskOperationException;
import ir.maktabsharif.finalproject.exception.SuggestionOperationException;

import java.time.LocalDate;
import java.time.LocalTime;

public interface SpecialistSuggestionService {
    SuggestionDto createSuggestionForOrder(SuggestionDto suggestionDto) throws SuggestionOperationException, InvalidFieldValueException, SpecialistOperationException, SubTaskOperationException;

}
