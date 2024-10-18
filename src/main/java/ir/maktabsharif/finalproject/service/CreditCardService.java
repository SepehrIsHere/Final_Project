package ir.maktabsharif.finalproject.service;

import ir.maktabsharif.finalproject.dto.CreditCardDto;
import ir.maktabsharif.finalproject.entities.CreditCard;

public interface CreditCardService {
    CreditCardDto add(CreditCard creditCard);

    void update(CreditCardDto creditCardDto);

    void delete(CreditCardDto creditCardDto);

    CreditCardDto findByCardNumber(String cardNumber);

    CreditCardDto findByCardNumberAndCvv2(String cardNumber, String cvv2);

}
