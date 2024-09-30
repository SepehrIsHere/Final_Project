package service.impl;

import entities.Order;
import entities.Suggestions;
import repository.SuggestionsRepository;
import service.SuggestionsService;
import util.ValidationUtil;

import java.util.List;

public class SuggestionsServiceImpl implements SuggestionsService {
    private final SuggestionsRepository suggestionsRepository;

    public SuggestionsServiceImpl(SuggestionsRepository suggestionsRepository) {
        this.suggestionsRepository = suggestionsRepository;
    }

    @Override
    public void add(Suggestions suggestion) {
        try {
            suggestionsRepository.add(suggestion);
        } catch (Exception e) {
            System.out.println("An error occured while adding suggestion" + e.getMessage());
        }
    }

    @Override
    public void update(Suggestions suggestion) {
        try {
            suggestionsRepository.update(suggestion);
        } catch (Exception e) {
            System.out.println("An error occured while updating suggestion" + e.getMessage());
        }
    }

    @Override
    public void delete(Suggestions suggestion) {
        try {
            suggestionsRepository.delete(suggestion);
        } catch (Exception e) {
            System.out.println("An error occured while deleting suggestion" + e.getMessage());
        }
    }

    @Override
    public Suggestions findById(int id) {
        try {
            return suggestionsRepository.findById(id);
        } catch (Exception e) {
            System.out.println("An error occured while finding suggestion" + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Suggestions> findAll() {
        try {
            return suggestionsRepository.findAll();
        } catch (Exception e) {
            System.out.println("An error occured while finding suggestions" + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Suggestions> findByOrder(Order order) {
        try {
            suggestionsRepository.findByOrder(order);
        } catch (Exception e) {
            System.out.println("An error occured while finding suggestions" + e.getMessage());
        }
        return null;
    }
}
