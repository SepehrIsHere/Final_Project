package ir.maktabsharif.finalproject.dto;

import ir.maktabsharif.finalproject.entities.Task;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubTaskDto {
    private String subTaskName;
    private String subTaskDescription;
    private double basePrice;
    private String taskName;
}
