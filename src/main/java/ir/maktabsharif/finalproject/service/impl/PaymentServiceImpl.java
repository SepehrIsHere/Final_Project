package ir.maktabsharif.finalproject.service.impl;

import ir.maktabsharif.finalproject.dto.CreditCardDto;
import ir.maktabsharif.finalproject.dto.ReceiptDto;
import ir.maktabsharif.finalproject.entities.*;
import ir.maktabsharif.finalproject.enumerations.OrderStatus;
import ir.maktabsharif.finalproject.exception.*;
import ir.maktabsharif.finalproject.service.*;
import ir.maktabsharif.finalproject.util.MapperUtil;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final CustomerService customerService;
    private final SpecialistService specialistService;
    private final SuggestionsService suggestionsService;
    private final ReceiptService receiptService;
    private final OrderService orderService;
    private final ValidationUtil validationUtil;

    @Override
    public void payWithCustomerCredit(String nameOfOrder) {
        try {
            ReceiptDto receiptDto = receiptService.findByNameOfOrder(nameOfOrder);
            Specialist specialist =
                    specialistService.findByFirstNameAndLastName(receiptDto.getSpecialistFirstName(),
                            receiptDto.getSpecialistLastName());
            Customer customer =
                    customerService.findByFirstNameAndLastName(receiptDto.getCustomerFirstName(),
                            receiptDto.getCustomerLastName());
            Order order =
                    orderService.findByNameOfOrder(receiptDto.getNameOfOrder());
            if (canPayForOrder(receiptDto)) {
                if (customer.getCredit() >= receiptDto.getTotalAmount() && customer.getCredit() != 0 ) {
                    customer.setCredit(customer.getCredit() - receiptDto.getTotalAmount());
                    customerService.update(customer);
                    order.setStatus(OrderStatus.PAID);
                    orderService.update(order);
                    specialist.setScore(specialist.getScore() + calculateAmount(receiptDto.getTotalAmount()));
                    specialistScoreUpdate(specialist, receiptDto);
                    specialistService.update(specialist);
                } else {
                    throw new NotEnoughCredit("You dont have enough credit");
                }
            } else {
                throw new PaymentFailedException("Failed to pay using customer credit");
            }
        } catch (Exception e) {
            throw new NotEnoughCreditException("An error occured while trying to pay from credit ");
        }
    }

    boolean canPayForOrder(ReceiptDto receiptDto) throws SuggestionOperationException {
        Suggestions suggestions = suggestionsService.findSuggestionsByNameOfOrderAndSpecialist(receiptDto.getNameOfOrder(), receiptDto.getSpecialistFirstName(), receiptDto.getSpecialistLastName());
        Order order = orderService.findByNameOfOrder(suggestions.getOrder().getNameOfOrder());
        if (suggestions != null) {
            if (LocalTime.now().isAfter(suggestions.getWorkTime()) && LocalDate.now().isAfter(suggestions.getSuggestedDate())) {
                return true;
            } else {
                throw new InvalidDateException("Date is not valid ! ");
            }
        } else {
            throw new SuggestionNotFound("Suggestion Not Found ");
        }
    }

    @Override
    public boolean processPayment(CreditCardDto creditCardDto, ReceiptDto receiptDto) {
        return validationUtil.isValid(creditCardDto) && validationUtil.isValid(receiptDto);
    }

    private void specialistScoreUpdate(Specialist specialist, ReceiptDto receiptDto) throws SuggestionOperationException, SpecialistOperationException {
        LocalDateTime timeOfService = LocalDateTime.of(receiptDto.getDateOfService(), receiptDto.getTimeOfService());
        long lateHours = ChronoUnit.HOURS.between(timeOfService, LocalDateTime.now());
        specialist.setScore(specialist.getScore() - (double) lateHours);
    }

    private Double calculateAmount(double amount) {
        return amount * 0.7;
    }

}
