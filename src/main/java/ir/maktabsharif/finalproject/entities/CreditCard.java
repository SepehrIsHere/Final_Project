package ir.maktabsharif.finalproject.entities;

import ir.maktabsharif.finalproject.enumerations.Bank;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class CreditCard extends BaseEntity {
    @NotBlank
    @Size(min = 12 ,max =16,message = "card number cant be smaller than 12 digits and bigger than 16")
    private String cardNumber;

    @Future
    @NotNull(message = "expiry date is invalid ")
    private LocalDate expiryDate;

    @NotBlank
    @Size(min = 3, max = 4,message = " cvv2 cant be empty or smaller than 3 and bigger than 4 ")
    private String cvv2;

    @Enumerated(EnumType.STRING)
    private Bank bank;
}
