package ir.maktabsharif.finalproject.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditCardDto {
    private String cardNumber;
    private LocalDate expiryDate;
    private String cvv2;
}
