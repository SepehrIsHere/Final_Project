package ir.maktabsharif.finalproject.service.impl;


import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.entities.Suggestions;
import ir.maktabsharif.finalproject.exception.CustomerOperationException;
import ir.maktabsharif.finalproject.exception.SuggestionOperationException;
import ir.maktabsharif.finalproject.repository.CustomerRepository;
import ir.maktabsharif.finalproject.service.CustomerService;
import ir.maktabsharif.finalproject.service.OrderService;
import ir.maktabsharif.finalproject.service.SuggestionsService;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final ValidationUtil validationUtil;
    private final CustomerRepository customerRepository;
    private final OrderService orderService;
    private final SuggestionsService suggestionsService;

    @Override
    public void add(Customer customer) throws CustomerOperationException {
        try {
            if (validationUtil.isValid(customer)) {
                customerRepository.save(customer);
            } else {
                validationUtil.displayViolations(customer);
            }
        } catch (Exception e) {
            throw new CustomerOperationException("An error occured while adding customer", e);
        }
    }

    @Override
    public void update(Customer customer) throws CustomerOperationException {
        try {
            customerRepository.save(customer);
        } catch (Exception e) {
            throw new CustomerOperationException("An error occured while updating customer", e);
        }
    }

    @Override
    public void delete(Customer customer) throws CustomerOperationException {
        try {
            customerRepository.delete(customer);
        } catch (Exception e) {
            throw new CustomerOperationException("An error occured while deleting customer", e);
        }
    }

    @Override
    public List<Customer> findAll() throws CustomerOperationException {
        try {
            return customerRepository.findAll();
        } catch (Exception e) {
            throw new CustomerOperationException("An error occured while finding all customers", e);
        }
    }

    @Override
    public Customer findById(int id) throws CustomerOperationException {
        try {
            return customerRepository.findCustomerById(id);
        } catch (Exception e) {
            throw new CustomerOperationException("An error occured while finding customer", e);
        }
    }

    @Override
    public Customer findByFirstNameAndLastName(String firstName, String lastName) throws CustomerOperationException {
        try {
            return customerRepository.findCustomerByFirstNameAndLastName(firstName, lastName);
        } catch (Exception e) {
            throw new CustomerOperationException("An error occured while finding customer", e);
        }
    }

    @Override
    public void displaySuggestionsOfOrder(Customer customer, Order order) throws SuggestionOperationException {
        try {
            List<Suggestions> suggestionsBySpecialistScore = suggestionsService.findByOrderOrderBySpecialistScoreDesc(order);
            List<Suggestions> suggestionsBySuggestedPrice = suggestionsService.findByOrderOrderBySuggestedPriceDesc(order);
            System.out.println("Orders based on specialist's score : ");
            displaySuggestionsOfOrder(suggestionsBySpecialistScore);
            System.out.println("Orders based on suggested price : ");
            displaySuggestionsOfOrder(suggestionsBySuggestedPrice);
        } catch (Exception e) {
            throw new SuggestionOperationException("An error occured while finding customer", e);
        }
    }

    private void displaySuggestionsOfOrder(List<Suggestions> suggestions) {
        if (suggestions != null) {
            for (Suggestions suggestion : suggestions) {
                System.out.println("Suggestion number : " + suggestion.getId());
                System.out.println("Suggested price : " + suggestion.getSuggestedPrice());
                System.out.println("Specialist : " + suggestion.getSpecialist().getFirstName() + " " + suggestion.getSpecialist().getLastName());
                System.out.println("Suggested date : " + suggestion.getSuggestedDate());
            }
        } else {
            System.out.println("List is empty ! ");
        }

    }
}
