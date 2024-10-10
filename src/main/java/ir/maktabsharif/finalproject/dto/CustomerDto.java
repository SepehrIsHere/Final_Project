package ir.maktabsharif.finalproject.dto;

import ir.maktabsharif.finalproject.entities.Order;
import lombok.Builder;

import java.util.List;

@Builder
public record CustomerDto (String firstname, String lastname, String username,
                             String password, double credit, List<Order> customerOrders){

}
