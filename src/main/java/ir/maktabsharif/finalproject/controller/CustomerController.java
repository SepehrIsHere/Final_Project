package ir.maktabsharif.finalproject.controller;

import ir.maktabsharif.finalproject.dto.CommentDto;
import ir.maktabsharif.finalproject.dto.CustomerDto;
import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.dto.SuggestionDto;
import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.enumerations.Role;
import ir.maktabsharif.finalproject.exception.CustomerOperationException;
import ir.maktabsharif.finalproject.exception.SuggestionOperationException;
import ir.maktabsharif.finalproject.repository.OrderRepository;
import ir.maktabsharif.finalproject.service.*;
import ir.maktabsharif.finalproject.specification.CustomerSpecification;
import ir.maktabsharif.finalproject.specification.OrderSpecification;
import ir.maktabsharif.finalproject.util.MapperUtil;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final OrderService orderService;
    private final SuggestionsService suggestionsService;
    private final CommentService commentService;
    private final OrderDisplayService orderDisplayService;
    private final MapperUtil mapperUtil;


    @GetMapping("admin/customers/all")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public List<CustomerDto> getAll() throws CustomerOperationException {
        return customerService.findAll().stream().map(mapperUtil::convertToDto).toList();
    }

    @PostMapping("register-customer")
    public CustomerDto createCustomer(@RequestBody CustomerDto customerDto) throws CustomerOperationException {
        return customerService.
                createCustomer(customerDto);
    }

    @DeleteMapping("admin/customers/{firstName}/{lastName}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    void deleteCustomer(@PathVariable String firstName, @PathVariable String lastName) throws CustomerOperationException {
        customerService.delete(customerService.findByFirstNameAndLastName(firstName, lastName));
    }

    @GetMapping("customer/suggestions/specialist-score/{nameOfOrder}")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMINISTRATOR')")
    List<SuggestionDto> displaySuggestionBySpecialistScore(@PathVariable String nameOfOrder) throws SuggestionOperationException {
        Order order = orderService.findByNameOfOrder(nameOfOrder);
        return suggestionsService.findByOrderOrderBySpecialistScoreDesc(order).stream().map(mapperUtil::convertToDto).toList();
    }

    @GetMapping("customer/suggestions/suggested-price/{nameOfOrder}")
    @PreAuthorize("hasRole('CUSTOMER')")
    List<SuggestionDto> displayBySuggestedPrice(@PathVariable String nameOfOrder) throws SuggestionOperationException {
        Order order = orderService.findByNameOfOrder(nameOfOrder);
        return suggestionsService.findByOrderOrderBySuggestedPriceDesc(order).stream().map(mapperUtil::convertToDto).toList();
    }

    @PostMapping("customer/comments/send")
    @PreAuthorize("hasRole('CUSTOMER')")
    CommentDto sendCommentForSpecialist(@RequestBody CommentDto commentDto) {
        commentService.addAComment(commentDto);
        return commentDto;
    }

    @GetMapping("customer/display/credit/{customerFirstName}/{customerLastName}")
    @PreAuthorize("hasRole('CUSTOMER')")
    String displayCustomerCredit(@PathVariable("customerFirstName") String customerFirstName, @PathVariable("customerLastName") String customerLastName) throws CustomerOperationException {
        double customerCredit = customerService.getCustomerCredit(customerFirstName, customerLastName);
        return "Your credit is : " + customerCredit;
    }

    @GetMapping("customer/display/orders/{customerFirstName}/{customerLastName}")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    List<OrderDto> displayCustomerOrders(@PathVariable("customerFirstName") String customerFirstName,@PathVariable("customerLastName") String customerLastName) throws CustomerOperationException {
       return orderDisplayService.getCustomerHistory(customerFirstName,customerLastName)
               .stream()
               .map(mapperUtil::convertToDto)
               .toList();
    }
}
