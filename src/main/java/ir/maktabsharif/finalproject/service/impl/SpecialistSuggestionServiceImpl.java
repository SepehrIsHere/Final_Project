package ir.maktabsharif.finalproject.service.impl;

import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.dto.SpecialistDto;
import ir.maktabsharif.finalproject.dto.SuggestionDto;
import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.entities.Specialist;
import ir.maktabsharif.finalproject.entities.SubTask;
import ir.maktabsharif.finalproject.entities.Suggestions;
import ir.maktabsharif.finalproject.enumerations.OrderStatus;
import ir.maktabsharif.finalproject.exception.*;
import ir.maktabsharif.finalproject.repository.OrderRepository;
import ir.maktabsharif.finalproject.repository.SpecialistRepository;
import ir.maktabsharif.finalproject.repository.SuggestionsRepository;
import ir.maktabsharif.finalproject.service.*;
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
    private final SpecialistRepository specialistRepository;
    private final SubTaskService subTaskService;
    private final SpecialistService specialistService;
    private final SuggestionsService suggestionsService;
    private final MapperUtil mapperUtil;

    @Override
    public SuggestionDto createSuggestionForOrder(SuggestionDto suggestionDto) throws SuggestionOperationException, InvalidFieldValueException, SpecialistOperationException, SubTaskOperationException {
        Order order = orderRepository.findByNameOfOrder(suggestionDto.getNameOfOrder());
        Specialist specialist = specialistRepository.findByFirstNameAndLastName(suggestionDto.getSpecialistFirstName(), suggestionDto.getSpecialistLastName());
        if (order != null) {
            if (specialist != null) {
                if (order.getStatus().equals(OrderStatus.WAITING_FOR_SPECIALIST_SELECTION) || order.getStatus().equals(OrderStatus.WAITING_FOR_SPECIALIST_PROPOSALS)) {
                    if (suggestionDto.getSuggestedPrice() >= order.getSubTask().getBasePrice()) {
                        if(isSpecialistAssignedToSubTask(order.getSubTask().getName(),suggestionDto.getSpecialistFirstName(),suggestionDto.getSpecialistLastName())) {
                            Suggestions suggestions = Suggestions.builder()
                                    .specialist(specialist)
                                    .order(order)
                                    .suggestedPrice(suggestionDto.getSuggestedPrice())
                                    .suggestedDate(suggestionDto.getSuggestedDate())
                                    .workTime(suggestionDto.getSuggestedTime())
                                    .build();
                            suggestionsService.add(suggestions);
                            List<Suggestions> orderSuggestions = order.getSuggestions();
                            orderSuggestions.add(suggestions);
                            order.setSuggestions(orderSuggestions);
                            orderRepository.save(order);
                            return mapperUtil.convertToDto(suggestions);
                        }else{
                            throw new SpecialistIsNotAssignedException("Specialist is not assigned to the following subtask");
                        }
                    } else {
                        throw new InvalidFieldValueException("Suggested price is invalid");
                    }
                } else {
                    throw new InvalidOrderStatus("Order Status is invalid ! ");
                }
            } else {
                throw new SpecialistNotFoundException("Specialist Not Found ! ");
            }
        } else {
            throw new OrderNotFoundException("Order  Not Found !");
        }
    }


    boolean isSpecialistAssignedToSubTask(String subTaskName, String specialistFirstName, String specialistLastName) throws SpecialistOperationException, SubTaskOperationException {
        Specialist specialist = specialistService.findByFirstNameAndLastName(specialistFirstName, specialistLastName);
        SubTask subTask = subTaskService.findByName(subTaskName);
        List<SubTask> specialistSubTasks = specialist.getSubTasks();
        if (specialistSubTasks != null) {
            return specialistSubTasks.contains(subTask);
        }else{
            return false;
        }
    }
}
