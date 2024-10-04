package ir.maktabsharif.finalproject.service.impl;


import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.entities.Specialist;
import ir.maktabsharif.finalproject.entities.SubTask;
import ir.maktabsharif.finalproject.entities.Suggestions;
import ir.maktabsharif.finalproject.enumerations.OrderStatus;
import ir.maktabsharif.finalproject.enumerations.SpecialistStatus;
import ir.maktabsharif.finalproject.exception.SpecialistOperationException;
import ir.maktabsharif.finalproject.repository.SpecialistRepository;
import ir.maktabsharif.finalproject.service.OrderService;
import ir.maktabsharif.finalproject.service.SpecialistService;
import ir.maktabsharif.finalproject.service.SuggestionsService;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialistServiceImpl implements SpecialistService {
    private final ValidationUtil validationUtil;
    private final SpecialistRepository specialistRepository;
    private SuggestionsService suggestionsService;
    private OrderService orderService;
    @Override
    public void add(Specialist specialist) throws SpecialistOperationException {
        try {
            if (validationUtil.isValid(specialist)) {
                specialistRepository.save(specialist);
            } else {
                validationUtil.displayViolations(specialist);
            }
        } catch (Exception e) {
            throw new SpecialistOperationException("An error occured while adding specialist", e);
        }

    }

    @Override
    public void update(Specialist specialist) throws SpecialistOperationException {
        try {
            specialistRepository.save(specialist);
        } catch (Exception e) {
            throw new SpecialistOperationException("An error occured while updating specialist", e);
        }

    }

    @Override
    public void delete(Specialist specialist) throws SpecialistOperationException {
        try {
            specialistRepository.delete(specialist);
        } catch (Exception e) {
            throw new SpecialistOperationException("An error occured while deleting specialist", e);
        }
    }

    @Override
    public List<Specialist> findAll() throws SpecialistOperationException {
        try {
            return specialistRepository.findAll();
        } catch (Exception e) {
            throw new SpecialistOperationException("An error occured while finding all specialists", e);
        }
    }

    @Override
    public Specialist findById(int id) throws SpecialistOperationException {
        try {
            return specialistRepository.findSpecialistById(id);
        } catch (Exception e) {
            throw new SpecialistOperationException("An error occured while finding specialist", e);
        }
    }

    @Override
    public Specialist findByFirstNameAndLastName(String firstName, String lastName) throws SpecialistOperationException {
        try {
            return specialistRepository.findByFirstNameAndLastName(firstName, lastName);
        } catch (Exception e) {
            throw new SpecialistOperationException("An error occured while finding specialist", e);
        }
    }

    @Override
    public boolean checkSpecialistImage(Specialist specialist) throws SpecialistOperationException {
        try {
            return specialist.getPersonalImage() != null;
        } catch (Exception e) {
            throw new SpecialistOperationException("An error occured while checking specialist personal image", e);
        }
    }

    @Override
    public Suggestions sendSuggestionsForRelatedSubTasks(Specialist specialist, Order order, Double suggestedPrice, LocalDate dateOfService, LocalTime timeOfWork) throws SpecialistOperationException {
        try {
            List<SubTask> specialistSubTask = specialist.getSubTasks();
            if (canRespondToSuggestion(specialistSubTask,specialist,order)) {
                if(suggestedPrice >= order.getSubTask().getBasePrice() && dateOfService.isAfter(order.getCreationDate())){
                    suggestionsService.add(Suggestions.builder()
                            .specialist(specialist)
                            .suggestedPrice(suggestedPrice)
                            .workTime(timeOfWork)
                            .suggestedDate(dateOfService)
                            .order(order)
                            .build());
                    order.setStatus(OrderStatus.WAITING_FOR_SPECIALIST_SELECTION);
                    orderService.update(order);
                }else{
                    System.out.println("You dont have any order to send suggestion ! ");
                    return null;
                }
            }else{
                System.out.println("You dont have any order to send suggestion ! ");
                return null;
            }
        } catch (Exception e) {
            throw new SpecialistOperationException("An error occured while sending suggestion from specialist", e);
        }
        return null;
    }

    private boolean canRespondToSuggestion(List<SubTask> specialistSubTask, Specialist specialist, Order order) {
        return !specialistSubTask.isEmpty() &&
                !(specialist.getSpecialistStatus().equals(SpecialistStatus.PENDING_APPROVAL)
                        || specialist.getSpecialistStatus().equals(SpecialistStatus.NOT_APPROVED))
                && order.getStatus().equals(OrderStatus.WAITING_FOR_SPECIALIST_SELECTION) ||
                order.getStatus().equals(OrderStatus.WAITING_FOR_SPECIALIST_PROPOSALS);
    }
}
