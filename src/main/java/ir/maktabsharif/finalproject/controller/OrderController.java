package ir.maktabsharif.finalproject.controller;

import ir.maktabsharif.finalproject.dto.OrderDto;
import ir.maktabsharif.finalproject.exception.CustomerOperationException;
import ir.maktabsharif.finalproject.exception.OrderOperationException;
import ir.maktabsharif.finalproject.service.CustomerOrderService;
import ir.maktabsharif.finalproject.service.OrderService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final CustomerOrderService customerOrderService;
    private final MapperUtil mapperUtil;

    @GetMapping("GET/orders")
    List<OrderDto> getOrders() throws OrderOperationException {
        return orderService.findAll().stream().map(mapperUtil::convertToDto).toList();
    }

    @PostMapping("POST/order")
    OrderDto createOrder(@RequestBody OrderDto orderDto) throws OrderOperationException {
      return customerOrderService.placeAnOrder(orderDto);
    }

    @DeleteMapping("DELETE/order/{nameOfOrder}")
    ResponseEntity<Void> deleteOrder(@PathVariable String nameOfOrder) throws OrderOperationException {
        orderService.delete(orderService.findByNameOfOrder(nameOfOrder));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("GET/orders/{nameOfOrder}")
    OrderDto findByNameOfOrder(@PathVariable String nameOfOrder) throws OrderOperationException {
        return mapperUtil.convertToDto(orderService.findByNameOfOrder(nameOfOrder));
    }

    @GetMapping("GET/orders/{customerFirstName}/{customerLastName}")
    List<OrderDto> findCustomersOrders(@PathVariable String customerFirstName, @PathVariable String customerLastName) throws OrderOperationException, CustomerOperationException {
        return customerOrderService.findCustomersOrders(customerFirstName, customerLastName);
    }
}
