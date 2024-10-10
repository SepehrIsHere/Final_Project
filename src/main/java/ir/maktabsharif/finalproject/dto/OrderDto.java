package ir.maktabsharif.finalproject.dto;

import ir.maktabsharif.finalproject.enumerations.OrderStatus;
import lombok.Builder;
import lombok.Setter;

import java.time.LocalDate;

@Builder
public record OrderDto(String nameOfOrder,double suggestedPrice, LocalDate dateOfService, OrderStatus orderStatus
        ,SpecialistDto specialistDto,CustomerDto customerDto,SubTaskDto subTaskDto) {
}
