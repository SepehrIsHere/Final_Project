package ir.maktabsharif.finalproject.service.impl;

import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.dto.SpecialistDto;
import ir.maktabsharif.finalproject.dto.SuggestionDto;
import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.entities.Specialist;
import ir.maktabsharif.finalproject.entities.Suggestions;
import ir.maktabsharif.finalproject.enumerations.OrderStatus;
import ir.maktabsharif.finalproject.exception.*;
import ir.maktabsharif.finalproject.repository.OrderRepository;
import ir.maktabsharif.finalproject.repository.SpecialistRepository;
import ir.maktabsharif.finalproject.repository.SuggestionsRepository;
import ir.maktabsharif.finalproject.service.CustomerSuggestionService;
import ir.maktabsharif.finalproject.service.OrderService;
import ir.maktabsharif.finalproject.service.SpecialistEditService;
import ir.maktabsharif.finalproject.service.SuggestionsService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerSuggestionServiceImpl implements CustomerSuggestionService {
    private final SpecialistRepository specialistRepository;
    private final MapperUtil mapperUtil;
    private final SuggestionsService suggestionsService;
    private final SuggestionsRepository suggestionsRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @Override
    public List<SuggestionDto> displaySuggestionsForOrder(OrderDto orderDto) {
        Order order = findOrder(orderDto);
        if (order != null) {
            List<Suggestions> orderSuggestions = suggestionsRepository.findByOrder(order);
            if (orderSuggestions != null && !orderSuggestions.isEmpty()) {
                List<SuggestionDto> suggestionDtos = new ArrayList<>();
                for (Suggestions suggestion : orderSuggestions) {
                    suggestionDtos.add(mapperUtil.convertToDto(suggestion));
                }
                return suggestionDtos;
            } else {
                throw new SuggestionListNotFound("No suggestions found for order ! ");
            }
        } else {
            throw new OrderNotFoundException("Order Not Found ! ");
        }
    }

    @Override
    public List<SuggestionDto> displaySuggestionBaseOnSpecialistRating(OrderDto orderDto) {
        if (getOrderSuggestions(orderDto) != null) {
            Order order = orderRepository.findByNameOfOrder(orderDto.nameOfOrder());
            return suggestionsRepository.findByOrderOrderBySpecialistScoreDesc(order)
                    .stream()
                    .map(mapperUtil::convertToDto)
                    .toList();
        }
        throw new OrderNotFoundException("Order Not Found ! ");
    }

    @Override
    public List<SuggestionDto> displaySuggestionBasedOnSuggestedPrice(OrderDto orderDto) {
        if (getOrderSuggestions(orderDto) != null) {
            Order order = orderRepository.findByNameOfOrder(orderDto.nameOfOrder());
            return suggestionsRepository.findByOrderOrderBySuggestedPriceDesc(order)
                    .stream()
                    .map(mapperUtil::convertToDto)
                    .toList();
        }
        throw new OrderNotFoundException("Order Not Found ! ");
    }

    private Order findOrder(OrderDto orderDto) {
        return orderRepository.findByNameOfOrder(orderDto.nameOfOrder());
    }

    List<Suggestions> getOrderSuggestions(OrderDto orderDto) {
        Order order = orderRepository.findByNameOfOrder(orderDto.nameOfOrder());
        return order.getSuggestions();
    }
}
