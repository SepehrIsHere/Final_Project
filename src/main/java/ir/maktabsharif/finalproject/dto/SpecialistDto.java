package ir.maktabsharif.finalproject.dto;

import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.entities.SubTask;
import lombok.Builder;

import java.util.List;

@Builder
public record SpecialistDto(String firstname, String lastname, String username,
                            double score, List<SubTask> specialistSubTask, List<Order> specialistOrders) {
}
