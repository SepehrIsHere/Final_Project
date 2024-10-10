package ir.maktabsharif.finalproject.dto;

import ir.maktabsharif.finalproject.entities.Task;
import lombok.Builder;

@Builder
public record SubTaskDto(String subTaskName, double basePrice, Task task) {
}
