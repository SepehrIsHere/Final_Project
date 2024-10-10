package ir.maktabsharif.finalproject.service.impl;


import ir.maktabsharif.finalproject.dto.CustomerDto;
import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.entities.Suggestions;
import ir.maktabsharif.finalproject.exception.CustomerNotFoundException;
import ir.maktabsharif.finalproject.exception.CustomerOperationException;
import ir.maktabsharif.finalproject.repository.CustomerRepository;
import ir.maktabsharif.finalproject.service.CustomerService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final ValidationUtil validationUtil;
    private final CustomerRepository customerRepository;
    private final MapperUtil mapperUtil;

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
            if (customer != null) {
                customerRepository.save(customer);
            } else {
                throw new CustomerNotFoundException("Customer Not Found");
            }
        } catch (Exception e) {
            throw new CustomerOperationException("An error occured while updating customer", e);
        }
    }

    @Override
    public void delete(CustomerDto customerDto) throws CustomerOperationException {
        try {
            Customer customer = customerRepository
                    .findCustomerByFirstNameAndLastName(mapperUtil.convertToEntity(customerDto).getFirstName(), mapperUtil.convertToEntity(customerDto).getLastName());
            if (customer != null) {
                customerRepository.delete(customer);
            } else {
                throw new CustomerNotFoundException("Customer Not Found");
            }
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
    public Customer findByFirstNameAndLastName(String firstName,String lastName) throws CustomerOperationException {
        try {
            Customer customer = customerRepository.findCustomerByFirstNameAndLastName(firstName,lastName);
            if (customer != null) {
                return customer;
            } else {
                throw new CustomerNotFoundException("Customer Not Found");
            }
        } catch (Exception e) {
            throw new CustomerOperationException("An error occured while finding customer", e);
        }
    }

    @Override
    public boolean doesCustomerExist(CustomerDto customerDto) {
        Customer customer = mapperUtil.convertToEntity(customerDto);
        return customer.getId() != null && customer.getFirstName() != null && customer.getLastName() != null;
    }

//    //    @Override
////    public void displaySuggestionsOfOrder(Customer customer, Order order) throws SuggestionOperationException {
////        try {
////            List<Suggestions> suggestionsBySpecialistScore = suggestionsService.findByOrderOrderBySpecialistScoreDesc(order);
////            List<Suggestions> suggestionsBySuggestedPrice = suggestionsService.findByOrderOrderBySuggestedPriceDesc(order);
////            System.out.println("Orders based on specialist's score : ");
////            displaySuggestionsOfOrder(suggestionsBySpecialistScore);
////            System.out.println("Orders based on suggested price : ");
////            displaySuggestionsOfOrder(suggestionsBySuggestedPrice);
////        } catch (Exception e) {
////            throw new SuggestionOperationException("An error occured while finding customer", e);
////        }
////    }
//
//    private void displaySuggestionsOfOrder(List<Suggestions> suggestions) {
//        if (suggestions != null) {
//            for (Suggestions suggestion : suggestions) {
//                System.out.println("Suggestion number : " + suggestion.getId());
//                System.out.println("Suggested price : " + suggestion.getSuggestedPrice());
//                System.out.println("Specialist : " + suggestion.getSpecialist().getFirstName() + " " + suggestion.getSpecialist().getLastName());
//                System.out.println("Suggested date : " + suggestion.getSuggestedDate());
//            }
//        } else {
//            System.out.println("List is empty ! ");
//        }
//    }
}
