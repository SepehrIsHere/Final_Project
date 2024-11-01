package ir.maktabsharif.finalproject.controller;

import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.dto.SuggestionDto;
import ir.maktabsharif.finalproject.exception.SuggestionOperationException;
import ir.maktabsharif.finalproject.service.CustomerSuggestionService;
import ir.maktabsharif.finalproject.service.OrderService;
import ir.maktabsharif.finalproject.service.SuggestionsService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerSuggestionController {
    private final CustomerSuggestionService customerSuggestionService;
    private final OrderService orderService;
    private final SuggestionsService suggestionsService;
    private final MapperUtil mapperUtil;

    @GetMapping("customer/suggestions/{nameOfOrder}")
    List<SuggestionDto> displaySuggestionForOrder(@PathVariable String nameOfOrder) throws SuggestionOperationException {
        Order order = orderService.findByNameOfOrder(nameOfOrder);
        return customerSuggestionService.displaySuggestionsForOrder(mapperUtil.convertToDto(order));
    }

    @GetMapping("customer/suggestions/by-specialist-score/{nameOfOrder}")
    List<SuggestionDto> displaySuggestionSpecialistFilter(@PathVariable String nameOfOrder) {
        Order order = orderService.findByNameOfOrder(nameOfOrder);
        return customerSuggestionService.displaySuggestionBaseOnSpecialistRating(mapperUtil.convertToDto(order));
    }

    @GetMapping("customer/suggestions/by-price/{nameOfOrder}")
    @PreAuthorize("hasRole('CUSTOMER')")
    List<SuggestionDto> displaySuggestionPriceFilter(@PathVariable String nameOfOrder) {
        Order order = orderService.findByNameOfOrder(nameOfOrder);
        return customerSuggestionService.displaySuggestionBasedOnSuggestedPrice(mapperUtil.convertToDto(order));
    }
}
