package ir.maktabsharif.finalproject.service.impl;


import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.entities.Suggestions;
import ir.maktabsharif.finalproject.exception.SuggestionOperationException;
import ir.maktabsharif.finalproject.repository.SuggestionsRepository;
import ir.maktabsharif.finalproject.service.SuggestionsService;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SuggestionsServiceImpl implements SuggestionsService {
    private final SuggestionsRepository suggestionsRepository;
    private final ValidationUtil validationUtil;

    @Override
    public void add(Suggestions suggestion) throws SuggestionOperationException {
        try {
            if (validationUtil.isValid(suggestion)) {
                suggestionsRepository.save(suggestion);
            } else {
                validationUtil.displayViolations(suggestion);
            }
        } catch (Exception e) {
            throw new SuggestionOperationException("An error occured while adding suggestion", e);
        }
    }

    @Override
    public void update(Suggestions suggestion) throws SuggestionOperationException {
        try {
            suggestionsRepository.save(suggestion);
        } catch (Exception e) {
            throw new SuggestionOperationException("An error occured while updating suggestion", e);
        }
    }

    @Override
    public void delete(Suggestions suggestion) throws SuggestionOperationException {
        try {
            suggestionsRepository.delete(suggestion);
        } catch (Exception e) {
            throw new SuggestionOperationException("An error occured while deleting suggestion", e);
        }
    }

    @Override
    public Suggestions findById(int id) throws SuggestionOperationException {
        try {
            return suggestionsRepository.findById(id);
        } catch (Exception e) {
            throw new SuggestionOperationException("An error occured while finding suggestion", e);
        }
    }

    @Override
    public List<Suggestions> findAll() throws SuggestionOperationException {
        try {
            return suggestionsRepository.findAll();
        } catch (Exception e) {
            throw new SuggestionOperationException("An error occured while finding all suggestions ", e);
        }
    }

    @Override
    public List<Suggestions> findByOrder(Order order) throws SuggestionOperationException {
        try {
            return suggestionsRepository.findByOrder(order);
        } catch (Exception e) {
            throw new SuggestionOperationException("An error occured while finding sugesstions by order ", e);
        }
    }

    @Override
    public List<Suggestions> findByOrderOrderBySpecialistScoreDesc(Order order) {
        try{
            suggestionsRepository.findByOrderOrderBySpecialistScoreDesc(order);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Suggestions> findByOrderOrderBySuggestedPriceDesc(Order order) {
        try{
            suggestionsRepository.findByOrderOrderBySuggestedPriceDesc(order);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return null;
    }
}
