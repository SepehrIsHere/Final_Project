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
import ir.maktabsharif.finalproject.service.OrderService;
import ir.maktabsharif.finalproject.service.SpecialistSuggestionService;
import ir.maktabsharif.finalproject.service.SuggestionsService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialistSuggestionServiceImpl implements SpecialistSuggestionService {
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final SpecialistRepository specialistRepository;
    private final SuggestionsService suggestionsService;
    private final MapperUtil mapperUtil;
    private final SuggestionsRepository suggestionsRepository;

    @Override
    public SuggestionDto createSuggestionForOrder(Double suggestedPrice, LocalDate suggestedDate, LocalTime suggestedTime, OrderDto orderDto, SpecialistDto specialistDto) throws SuggestionOperationException {
        Order order = orderRepository.findByNameOfOrder(orderDto.nameOfOrder());
        Specialist specialist = specialistRepository.findByFirstNameAndLastName(specialistDto.firstname(), specialistDto.lastname());
        if (order != null) {
            if (specialist != null) {
                if (order.getStatus().equals(OrderStatus.WAITING_FOR_SPECIALIST_SELECTION) || order.getStatus().equals(OrderStatus.WAITING_FOR_SPECIALIST_PROPOSALS)) {
                    Suggestions suggestions = Suggestions.builder()
                            .specialist(order.getSpecialist())
                            .order(order).suggestedPrice(suggestedPrice)
                            .suggestedDate(suggestedDate).workTime(suggestedTime)
                            .build();
                    List<Suggestions> orderSuggestions = order.getSuggestions();
                    orderSuggestions.add(suggestions);
                    order.setSuggestions(orderSuggestions);
                    orderRepository.save(order);
                    suggestionsService.add(suggestions);
                    return mapperUtil.convertToDto(suggestions);
                } else {
                    throw new InvalidOrderStatus("Order Status is invalid ! ");
                }
            } else {
                throw new SpecialistNotFoundException("Specialist Not Found ! ");
            }
        } else {
            throw new OrderNotFoundException("Order Not Found !");
        }
    }

    @Override
    public void removeSuggestionForOrder(SuggestionDto suggestionDto) throws SuggestionOperationException {
        Suggestions suggestions = suggestionsService.findById(suggestionDto.id());
        Order order = orderRepository.findByNameOfOrder(suggestionDto.orderDto().nameOfOrder());
        if (suggestions != null && order != null) {
            suggestionsRepository.delete(suggestions);
            List<Suggestions> orderSuggestions = order.getSuggestions();
            orderSuggestions.remove(suggestions);
            order.setSuggestions(orderSuggestions);
            orderRepository.save(order);
        } else {
            throw new SuggestionNotFound("Suggestion Not Found ");
        }
    }


}
