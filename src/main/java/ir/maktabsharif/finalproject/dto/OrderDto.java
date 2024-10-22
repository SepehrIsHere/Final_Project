package ir.maktabsharif.finalproject.dto;

import ir.maktabsharif.finalproject.enumerations.OrderStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class   OrderDto {
    private String nameOfOrder;
    private Double suggestedPrice;
    private LocalDate dateOfService;
    private OrderStatus orderStatus;
    private String specialistFirstName;
    private String specialistLastName;
    private String customerFirstName;
    private String customerLastName;
    private String subTaskName;
    private String description;

    public OrderDto(String nameOfOrder, Double suggestedPrice, LocalDate dateOfService, String customerFirstName, String customerLastName, String subTaskName, String description) {
        this.nameOfOrder = nameOfOrder;
        this.suggestedPrice = suggestedPrice;
        this.dateOfService = dateOfService;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.subTaskName = subTaskName;
        this.description = description;
    }
}
