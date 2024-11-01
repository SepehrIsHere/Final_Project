package ir.maktabsharif.finalproject.controller;

import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.enumerations.OrderStatus;
import ir.maktabsharif.finalproject.exception.CustomerOperationException;
import ir.maktabsharif.finalproject.exception.SpecialistOperationException;
import ir.maktabsharif.finalproject.exception.SubTaskOperationException;
import ir.maktabsharif.finalproject.exception.TaskOperationException;
import ir.maktabsharif.finalproject.service.OrderDisplayService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderDisplayController {
    private final OrderDisplayService orderDisplayService;
    private final MapperUtil mapperUtil;

    @GetMapping("history?customer/{customerFirstName}/{customerLastName}")
    @PreAuthorize("hasAnyRole('ADMINSTATOR,CUSTOMER')")
    List<OrderDto> getCustomerHistory(@PathVariable("customerFirstName") String customerFirstName, @PathVariable("customerLastName") String customerLastName) throws CustomerOperationException {
        return orderDisplayService.getCustomerHistory(customerFirstName, customerLastName)
                .stream()
                .map(mapperUtil::convertToDto)
                .toList();
    }

    @GetMapping("history?specialist/{specialistFirstName}/{specialistLastName}")
    @PreAuthorize("hasAnyRole('ADMINSTATOR,SPECIALIST')")
    List<OrderDto> getSpecialistHistory(@PathVariable("specialistFirstName") String specialistFirstName, @PathVariable("specialistLastName") String specialistLastName) throws SpecialistOperationException {
        return orderDisplayService.getSpecialistHistory(specialistFirstName, specialistLastName)
                .stream()
                .map(mapperUtil::convertToDto)
                .toList();
    }

    @GetMapping("history?task/{taskName}")
    @PreAuthorize("hasRole('ADMINSTATOR')")
    List<OrderDto> getTaskHistory(@PathVariable("taskName") String taskName) throws TaskOperationException {
        return orderDisplayService.getTaskHistory(taskName)
                .stream()
                .map(mapperUtil::convertToDto)
                .toList();
    }

    @GetMapping("history?subTask/{subTaskName}")
    @PreAuthorize("hasRole('ADMINSTATOR')")
    List<OrderDto> getSubTaskHistory(@PathVariable("subTaskName") String subTaskName) throws SubTaskOperationException {
        return orderDisplayService.getSubTaskHistory(subTaskName)
                .stream()
                .map(mapperUtil::convertToDto)
                .toList();
    }

    @GetMapping("history?orderStatus/{orderStatus}")
    @PreAuthorize("hasRole('ADMINSTATOR')")
    List<OrderDto> getOrderStatusHistory(@PathVariable("orderStatus") OrderStatus orderStatus) {
        return orderDisplayService.getOrderStatusHistory(orderStatus)
                .stream()
                .map(mapperUtil::convertToDto)
                .toList();
    }

    @GetMapping("history?order-by-date/{startDate}/{endDate}")
    @PreAuthorize("hasRole('ADMINSTATOR')")
    List<OrderDto> getOrderDateHistory(@PathVariable("startDate") LocalDate startDate, @PathVariable("endDate") LocalDate endDate) {
        return orderDisplayService.getDateHistory(startDate, endDate)
                .stream()
                .map(mapperUtil::convertToDto)
                .toList();
    }
}
