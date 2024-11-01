package ir.maktabsharif.finalproject.controller;

import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.enumerations.OrderStatus;
import ir.maktabsharif.finalproject.exception.CustomerOperationException;
import ir.maktabsharif.finalproject.exception.OrderOperationException;
import ir.maktabsharif.finalproject.exception.SuggestionOperationException;
import ir.maktabsharif.finalproject.service.CustomerOrderService;
import ir.maktabsharif.finalproject.service.CustomerService;
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
public class CustomerOrderController {
    private final CustomerOrderService customerOrderService;
    private final OrderService orderService;

    @PostMapping("customer/order/create")
    OrderDto placeAnOrder(@RequestBody OrderDto orderDto) throws OrderOperationException {
        return customerOrderService.placeAnOrder(orderDto);
    }

    @PatchMapping("customer/order/pick/{nameOfOrder}/{specialistFirstName}/{specialistLastName}")
    @PreAuthorize("hasRole('CUSTOMER')")
    ResponseEntity<String> pickSpecialistForOrder(@PathVariable String nameOfOrder,@PathVariable String specialistFirstName,@PathVariable String specialistLastName) throws OrderOperationException {
        customerOrderService.pickSpecialistForOrder(specialistFirstName,specialistLastName,nameOfOrder);
        return ResponseEntity.ok("Order picked by " + specialistFirstName + " " + specialistLastName);
    }

    @PatchMapping("customer/order/started/{nameOfOrder}/{specialistFirstName}/{specialistLastName}")
    @PreAuthorize("hasRole('CUSTOMER')")
    ResponseEntity<String> changeOrderStatusToStarted(@PathVariable String nameOfOrder,@PathVariable String specialistFirstName,@PathVariable String specialistLastName) throws OrderOperationException, SuggestionOperationException {
        customerOrderService.changeOrderStatusToStarted(nameOfOrder,specialistFirstName,specialistLastName);
        return ResponseEntity.ok("Order status changed to started");
    }

    @PatchMapping("customer/order/finished/{nameOfOrder}")
    @PreAuthorize("hasRole('CUSTOMER')")
    ResponseEntity<String> changeOrderStatusToFinished(@PathVariable String nameOfOrder) throws OrderOperationException {
        customerOrderService.changeOrderStatusToFinished(nameOfOrder);
        return ResponseEntity.ok("Order Status changed to finished ");
    }
}
