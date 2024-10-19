package ir.maktabsharif.finalproject.controller;

import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.entities.Customer;
import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.enumerations.OrderStatus;
import ir.maktabsharif.finalproject.exception.CustomerOperationException;
import ir.maktabsharif.finalproject.exception.OrderOperationException;
import ir.maktabsharif.finalproject.service.CustomerOrderService;
import ir.maktabsharif.finalproject.service.CustomerService;
import ir.maktabsharif.finalproject.service.OrderService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerOrderController {
    private final CustomerOrderService customerOrderService;
    private final OrderService orderService;

    @GetMapping("customer/orders/{firstName}/{lastName}")
    List<OrderDto> findCustomersOrders(@PathVariable String firstName, @PathVariable String lastName) throws CustomerOperationException {
        return customerOrderService.findCustomersOrders(firstName,lastName);
    }

    @PostMapping("/order")
    OrderDto placeAnOrder(@RequestBody OrderDto orderDto) throws OrderOperationException {
        return customerOrderService.placeAnOrder(orderDto);
    }

    @DeleteMapping("/order/{nameOfOrder}")
    ResponseEntity<Void> deleteAnOrder(@PathVariable String nameOfOrder) throws OrderOperationException {
        orderService.delete(orderService.findByNameOfOrder(nameOfOrder));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("order/started/{nameOfOrder}/{specialistFirstName}/{specialistLastName}")
    ResponseEntity<Void> changeOrderStatusToStarted(@PathVariable String nameOfOrder,@PathVariable String specialistFirstName,@PathVariable String specialistLastName) throws OrderOperationException {
        customerOrderService.changeOrderStatusToStarted(nameOfOrder,specialistFirstName,specialistLastName);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("order/finished/{nameOfOrder}")
    ResponseEntity<Void> changeOrderStatusToFinished(@PathVariable String nameOfOrder) throws OrderOperationException {
        customerOrderService.changeOrderStatusToFinished(nameOfOrder);
        return ResponseEntity.noContent().build();
    }
}
