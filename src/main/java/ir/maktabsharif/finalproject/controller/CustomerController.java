package ir.maktabsharif.finalproject.controller;

import ir.maktabsharif.finalproject.dto.CommentDto;
import ir.maktabsharif.finalproject.dto.CustomerDto;
import ir.maktabsharif.finalproject.dto.SuggestionDto;
import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.exception.CustomerOperationException;
import ir.maktabsharif.finalproject.exception.SuggestionOperationException;
import ir.maktabsharif.finalproject.service.CommentService;
import ir.maktabsharif.finalproject.service.CustomerService;
import ir.maktabsharif.finalproject.service.OrderService;
import ir.maktabsharif.finalproject.service.SuggestionsService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final OrderService orderService;
    private final SuggestionsService suggestionsService;
    private final CommentService commentService;
    private final MapperUtil mapperUtil;

    @GetMapping("GET/customers")
    public List<CustomerDto> getAll() throws CustomerOperationException {
        return customerService.findAll().stream().map(mapperUtil::convertToDto).toList();
    }

    @PostMapping("POST/customers")
    public CustomerDto createCustomer(@RequestBody CustomerDto customerDto) throws CustomerOperationException {
        return customerService.
                createCustomer(customerDto);
    }

    @DeleteMapping("DELETE/customers/{firstName}/{lastName}")
    void deleteCustomer(@PathVariable String firstName, @PathVariable String lastName) throws CustomerOperationException {
        customerService.delete(customerService.findByFirstNameAndLastName(firstName, lastName));
    }

    @GetMapping("GET/suggestions/specialist-score/{nameOfOrder}")
    List<SuggestionDto> displaySuggestionBySpecialistScore(@PathVariable String nameOfOrder) throws CustomerOperationException, SuggestionOperationException {
        Order order = orderService.findByNameOfOrder(nameOfOrder);
        return suggestionsService.findByOrderOrderBySpecialistScoreDesc(order).stream().map(mapperUtil::convertToDto).toList();
    }

    @GetMapping("GET/suggestions/suggested-price/{nameOfOrder}")
    List<SuggestionDto> displayBySuggestedPrice(@PathVariable String nameOfOrder) throws CustomerOperationException, SuggestionOperationException {
        Order order = orderService.findByNameOfOrder(nameOfOrder);
        return suggestionsService.findByOrderOrderBySuggestedPriceDesc(order).stream().map(mapperUtil::convertToDto).toList();
    }

    @PostMapping("POST/comments")
    CommentDto sendCommentForSpecialist(@RequestBody CommentDto commentDto) {
        commentService.addAComment(commentDto);
        return commentDto;
    }

}
