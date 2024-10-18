package ir.maktabsharif.finalproject.service.impl;

import ir.maktabsharif.finalproject.dto.CreditCardDto;
import ir.maktabsharif.finalproject.entities.CreditCard;
import ir.maktabsharif.finalproject.exception.CreditCardNotFoundException;
import ir.maktabsharif.finalproject.exception.CreditCardOperationException;
import ir.maktabsharif.finalproject.exception.InvalidFieldValueException;
import ir.maktabsharif.finalproject.repository.CreditCardRepository;
import ir.maktabsharif.finalproject.service.CreditCardService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import ir.maktabsharif.finalproject.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {
    private final CreditCardRepository creditCardRepository;
    private final ValidationUtil validationUtil;
    private final MapperUtil mapperUtil;

    @Override
    public CreditCardDto add(CreditCard creditCard) {
        try {
            if (validationUtil.isValid(creditCard)) {
                creditCardRepository.save(creditCard);
                return mapperUtil.convertToDto(creditCard);
            } else {
                throw new InvalidFieldValueException("Some fields of credit card are invalid");
            }

        } catch (Exception e) {
            throw new CreditCardOperationException("An error occured while finding credit card ! ");
        }
    }

    @Override
    public void update(CreditCardDto creditCardDto) {
        try {
            CreditCard creditCard = creditCardRepository.findByCardNumber(creditCardDto.getCardNumber());
            if (creditCard != null) {
                creditCardRepository.save(mapperUtil.convertToEntity(creditCardDto));
            } else {
                throw new CreditCardNotFoundException("Credit Card Not Found ! ");
            }
        } catch (Exception e) {
            throw new CreditCardOperationException("An error occured while finding credit card ! ");
        }
    }

    @Override
    public void delete(CreditCardDto creditCardDto) {
        try {
            CreditCard creditCard = creditCardRepository.findByCardNumber(creditCardDto.getCardNumber());
            if (creditCard != null) {
                creditCardRepository.delete(creditCard);
            } else {
                throw new CreditCardNotFoundException("Credit Card Not Found ! ");
            }
        } catch (Exception e) {
            throw new CreditCardOperationException("An error occured while finding credit card ! ");
        }
    }

    @Override
    public CreditCardDto findByCardNumber(String cardNumber) {
        try {
            CreditCard creditCard = creditCardRepository.findByCardNumber(cardNumber);
            if (creditCard != null) {
                return mapperUtil.convertToDto(creditCard);
            } else {
                throw new CreditCardNotFoundException("Credit Card Not Found ! ");
            }
        } catch (Exception e) {
            throw new CreditCardOperationException("An error occured while finding credit card ! ");
        }
    }

    @Override
    public CreditCardDto findByCardNumberAndCvv2(String cardNumber, String cvv2) {
        try {
            CreditCard creditCard = creditCardRepository.findByCardNumberAndCvv2(cardNumber, cvv2);
            if (creditCard != null) {
                return mapperUtil.convertToDto(creditCard);
            } else {
                throw new CreditCardNotFoundException("Credit Card Not Found ! ");
            }
        } catch (Exception e) {
            throw new CreditCardOperationException("An error occured while finding credit card ! ");
        }
    }
}
