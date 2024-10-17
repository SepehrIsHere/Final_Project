package ir.maktabsharif.finalproject.dto;

import ir.maktabsharif.finalproject.entities.Order;
import ir.maktabsharif.finalproject.entities.SubTask;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpecialistDto {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private double score;
    private List<SubTask> specialistSubTask;
    private List<Order> specialistOrders;
}
