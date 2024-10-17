package ir.maktabsharif.finalproject.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceiptDto {

    private String nameOfOrder;

    private String customerFirstName;

    private String customerLastName;

    private String specialistFirstName;

    private String specialistLastName;

    private Double totalAmount;

    private LocalDate dateOfService;

    private LocalTime timeOfService;

}
