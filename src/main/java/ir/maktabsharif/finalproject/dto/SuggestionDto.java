package ir.maktabsharif.finalproject.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record SuggestionDto(Integer id,Double suggestedPrice, LocalDate suggestedDate, LocalTime suggestedTime,
                            OrderDto orderDto,SpecialistDto specialistDto) {
}
