package ir.maktabsharif.finalproject.service;

import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.dto.SpecialistDto;
import ir.maktabsharif.finalproject.exception.OrderOperationException;
import ir.maktabsharif.finalproject.exception.SpecialistOperationException;

import java.util.List;

public interface SpecialistOrderService {
    List<OrderDto> displayOrdersRelatedToSubTask(SpecialistDto specialistDto) throws  OrderOperationException;

    void pickOrder(OrderDto orderDto, SpecialistDto specialistDto) throws  OrderOperationException;

    List<OrderDto> displayWaitingForSelectionOrders() throws  OrderOperationException;
}
