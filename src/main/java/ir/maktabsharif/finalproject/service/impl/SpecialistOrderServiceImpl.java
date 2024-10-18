package ir.maktabsharif.finalproject.service.impl;

import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.dto.SpecialistDto;
import ir.maktabsharif.finalproject.dto.SubTaskDto;
import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.entities.Specialist;
import ir.maktabsharif.finalproject.enumerations.OrderStatus;
import ir.maktabsharif.finalproject.exception.OrderNotFoundException;
import ir.maktabsharif.finalproject.exception.OrderOperationException;
import ir.maktabsharif.finalproject.exception.SpecialistOperationException;
import ir.maktabsharif.finalproject.exception.SubTaskNotFoundException;
import ir.maktabsharif.finalproject.service.SpecialistOrderService;
import ir.maktabsharif.finalproject.service.SpecialistService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialistOrderServiceImpl implements SpecialistOrderService {
    private final SpecialistService specialistService;
    private final OrderServiceImpl orderService;
    private final MapperUtil mapperUtil;

    @Override
    public List<OrderDto> displayOrdersRelatedToSubTask(SpecialistDto specialistDto) throws OrderOperationException {
        try {
            Specialist specialist = specialistService.findByFirstNameAndLastName(specialistDto.getFirstname(), specialistDto.getLastname());
            List<SubTaskDto> subTasks = specialist.getSubTasks()
                    .stream()
                    .map(mapperUtil::convertToDto)
                    .toList();
            if (subTasks != null && !subTasks.isEmpty()) {
                List<OrderDto> relatedOrders = new ArrayList<>();
                for (SubTaskDto subTaskDto : subTasks) {
                    relatedOrders = orderService.findByRelatedSubTask(subTaskDto);
                }
                return relatedOrders;
            } else {
                throw new SubTaskNotFoundException("No related SubTasks found !");
            }
        } catch (Exception e) {
            throw new OrderOperationException("Failed to find related orders of subtask", e);
        }
    }

    @Override
    public void pickOrder(OrderDto orderDto, SpecialistDto specialistDto) throws OrderOperationException {
        try {
            Specialist specialist = specialistService.findByFirstNameAndLastName(specialistDto.getFirstname(),
                    specialistDto.getLastname());
            Order order = orderService.findByNameOfOrder(orderDto.getNameOfOrder());

            List<Order> specialistOrders = specialist.getOrders();
            specialistOrders.add(order);
            specialist.setOrders(specialistOrders);
            specialistService.update(specialist);

            order.setSpecialist(specialist);
            order.setStatus(OrderStatus.WAITING_FOR_SPECIALIST_ARRIVAL);
            orderService.update(order);
        } catch (Exception e) {
            throw new OrderOperationException("Failed to assign order to specialist", e);
        }
    }

    @Override
    public List<OrderDto> displayWaitingForSelectionOrders() throws OrderOperationException {
        try {
            List<OrderDto> orders = orderService.findWaitingForSelectionOrders()
                    .stream()
                    .map(mapperUtil::convertToDto)
                    .toList();
            if (orders != null && !orders.isEmpty()) {
                return orders;
            } else {
                throw new OrderNotFoundException("Orders Not Found ");
            }
        } catch (Exception e) {
            throw new OrderOperationException("Failed to display orders ", e);
        }
    }
}
