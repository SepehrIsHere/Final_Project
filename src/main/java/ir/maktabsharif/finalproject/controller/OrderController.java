package ir.maktabsharif.finalproject.controller;

import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.exception.CustomerOperationException;
import ir.maktabsharif.finalproject.exception.OrderOperationException;
import ir.maktabsharif.finalproject.service.CustomerOrderService;
import ir.maktabsharif.finalproject.service.OrderService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final CustomerOrderService customerOrderService;
    private final MapperUtil mapperUtil;

    @GetMapping("get-all/orders")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    List<OrderDto> getOrders() throws OrderOperationException {
        return orderService.findAll().stream().map(mapperUtil::convertToDto).toList();
    }

    @DeleteMapping("admin/delete-order/{nameOfOrder}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    ResponseEntity<Void> deleteOrder(@PathVariable String nameOfOrder) throws OrderOperationException {
        orderService.delete(orderService.findByNameOfOrder(nameOfOrder));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("admin/find-name/{nameOfOrder}")
    OrderDto findByNameOfOrder(@PathVariable String nameOfOrder) throws OrderOperationException {
        return mapperUtil.convertToDto(orderService.findByNameOfOrder(nameOfOrder));
    }

    @GetMapping("customer-related/orders/{customerFirstName}/{customerLastName}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','CUSTOMER')")
    List<OrderDto> findCustomersOrders(@PathVariable String customerFirstName, @PathVariable String customerLastName) throws OrderOperationException, CustomerOperationException {
        return customerOrderService.findCustomersOrders(customerFirstName, customerLastName);
    }
}
